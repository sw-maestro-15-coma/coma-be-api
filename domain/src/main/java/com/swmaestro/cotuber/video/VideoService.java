package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.batch.AIProcessQueue;
import com.swmaestro.cotuber.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.exception.ShortsMakingFailException;
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
    private final TopTitleGenerator topTitleGenerator;

    public VideoCreateResponseDto requestVideoDownload(final long userId, final VideoCreateRequestDto request) {
        final Video video = videoRepository.save(Video.initialVideo(request));
        final Shorts shorts = shortsRepository.save(Shorts.initialShorts(userId, video.getId(), "테스트 제목"));

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
        VideoDownloadResponse response;
        String generatedTopTitle;
        try {
            log.info("video download start");
            response = youtubeVideoDownloader.download(task.youtubeUrl());
            generatedTopTitle = topTitleGenerator.makeTopTitle(response.originalTitle());
        } catch (Exception e) {
            log.error("youtube 원본 영상 다운로드에 실패했습니다 : {}", e.getMessage());
            log.error("shorts id : {}", task.shortsId());

            final Shorts shorts = shortsRepository.findById(task.shortsId())
                    .orElseThrow();
            shorts.changeStateError();
            shortsRepository.save(shorts);

            throw new ShortsMakingFailException("youtube 원본 영상 다운로드 실패");
        }

        log.info("video download end");

        final Video video = videoRepository.findById(task.videoId())
                .orElseThrow();
        video.changeS3Url(response.s3Url());
        video.changeLength(response.length());
        video.changeTitle(response.originalTitle());

        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();
        shorts.changeProgressState(AI_PROCESSING);
        shorts.changeTopTitle(generatedTopTitle);

        videoRepository.save(video);
        shortsRepository.save(shorts);

        aiProcessQueue.push(
                AIProcessTask.builder()
                        .videoId(task.videoId())
                        .shortsId(task.shortsId())
                        .youtubeUrl(task.youtubeUrl())
                        .build()
        );
    }
}
