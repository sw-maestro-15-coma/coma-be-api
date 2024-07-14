package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.batch.AIProcessQueue;
import com.swmaestro.cotuber.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.video.dto.VideoDownloadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.swmaestro.cotuber.shorts.ProgressState.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final ShortsRepository shortsRepository;
    private final VideoDownloadQueue videoDownloadQueue;
    private final AIProcessQueue aiProcessQueue;
    private final YoutubeVideoDownloader youtubeVideoDownloader;

    public VideoCreateResponseDto requestVideoDownload(final long userId, final VideoCreateRequestDto request) {
        final Video video = videoRepository.save(Video.initialVideo(request));
        final Shorts shorts = shortsRepository.save(Shorts.initialShorts(userId, video.getId()));

        videoDownloadQueue.push(
                VideoDownloadTask.builder()
                        .videoId(video.getId())
                        .shortsId(shorts.getId())
                        .youtubeUrl(request.url())
                        .build()
        );

        return VideoCreateResponseDto.builder()
                .id(video.getId())
                .build();
    }

    public void downloadYoutube(final VideoDownloadTask task) {
        VideoDownloadResponse response = null;

        try {
            response = youtubeVideoDownloader.download(task.youtubeUrl());
        } catch (Exception e) {
            log.error("youtube 원본 영상 다운로드에 실패했습니다");
            log.error("shorts id : {}", task.shortsId());

            final Shorts shorts = shortsRepository.findById(task.shortsId());
            shorts.errorState();
            shortsRepository.save(shorts);
        }

        final Video video = videoRepository.findById(task.videoId());
        video.changeS3Url(response.s3Url());
        video.changeLength(response.length());

        final Shorts shorts = shortsRepository.findById(task.shortsId());
        shorts.changeProgressState(AI_PROCESSING);

        videoRepository.save(video);
        shortsRepository.save(shorts);

        aiProcessQueue.push(
                AIProcessTask.builder()
                        .shortsId(task.shortsId())
                        .youtubeUrl(task.youtubeUrl())
                        .build()
        );
    }
}
