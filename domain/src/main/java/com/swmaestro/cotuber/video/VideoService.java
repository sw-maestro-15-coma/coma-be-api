package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.batch.AIProcessQueue;
import com.swmaestro.cotuber.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.exception.ShortsMakingFailException;
import com.swmaestro.cotuber.log.Log;
import com.swmaestro.cotuber.log.LogRepository;
import com.swmaestro.cotuber.log.LogService;
import com.swmaestro.cotuber.log.ProgressContext;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.video.dto.VideoDownloadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.swmaestro.cotuber.log.ProgressContext.GPT_TITLE_GENERATING;
import static com.swmaestro.cotuber.log.ProgressContext.YOUTUBE_DOWNLOADING;
import static com.swmaestro.cotuber.shorts.ProgressState.AI_PROCESSING;

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
    private final LogService logService;

    public VideoCreateResponseDto requestVideoDownload(final long userId, final VideoCreateRequestDto request) {
        final Video video = videoRepository.save(Video.initialVideo(request));
        final Shorts shorts = shortsRepository.save(Shorts.initialShorts(userId, video.getId(), "테스트 제목"));

        videoDownloadQueue.push(
                VideoDownloadTask.builder()
                        .userId(userId)
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
        VideoDownloadResponse response = downloadVideo(task);
        String generatedTopTitle = generateTopTitle(task, response);

        updateVideoStatus(task, response);
        updateShortsStatus(task, generatedTopTitle);

        aiProcessQueue.push(
                AIProcessTask.builder()
                        .userId(task.userId())
                        .videoId(task.videoId())
                        .shortsId(task.shortsId())
                        .youtubeUrl(task.youtubeUrl())
                        .build()
        );
    }

    private VideoDownloadResponse downloadVideo(VideoDownloadTask task) {
        try {
            log.info("video download start");
            VideoDownloadResponse response = youtubeVideoDownloader.download(task.youtubeUrl());
            log.info("video download end");
            logService.sendSuccessLog(task.userId(), task.shortsId(), YOUTUBE_DOWNLOADING);
            
            return response;
        } catch (Exception e) {
            log.error("youtube 원본 영상 다운로드에 실패했습니다 : {}", e.getMessage());
            setShortsStatusToError(task.shortsId());
            logService.sendFailLog(task.userId(), task.shortsId(), YOUTUBE_DOWNLOADING, e.getMessage());
            throw new ShortsMakingFailException("youtube 원본 영상 다운로드 실패");
        }
    }

    private String generateTopTitle(VideoDownloadTask task, VideoDownloadResponse response) {
        try {
            String generatedTopTitle = topTitleGenerator.makeTopTitle(response.originalTitle());
            logService.sendSuccessLog(task.userId(), task.shortsId(), GPT_TITLE_GENERATING);

            return generatedTopTitle;
        } catch (Exception e) {
            log.error("top title 생성에 실패했습니다 : {}", e.getMessage());
            setShortsStatusToError(task.shortsId());
            logService.sendFailLog(task.userId(), task.shortsId(), GPT_TITLE_GENERATING, e.getMessage());
            throw new ShortsMakingFailException("top title 생성 실패");
        }
    }

    private void setShortsStatusToError(long shortsId) {
        final Shorts shorts = shortsRepository.findById(shortsId)
                .orElseThrow();

        shorts.changeStateError();
        shortsRepository.save(shorts);
    }

    private void updateVideoStatus(
            VideoDownloadTask task,
            VideoDownloadResponse response
    ) {
        final Video video = videoRepository.findById(task.videoId())
                .orElseThrow();

        video.updateVideoInfo(response);
        videoRepository.save(video);
    }

    private void updateShortsStatus(
            VideoDownloadTask task,
            String generatedTopTitle
    ) {
        final Shorts shorts = shortsRepository.findById(task.shortsId())
                .orElseThrow();

        shorts.changeProgressState(AI_PROCESSING);
        shorts.changeTopTitle(generatedTopTitle);

        shortsRepository.save(shorts);
    }
}
