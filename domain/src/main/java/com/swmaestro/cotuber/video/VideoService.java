package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.batch.AIProcessQueue;
import com.swmaestro.cotuber.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.batch.dto.AIProcessTask;
import com.swmaestro.cotuber.batch.dto.VideoDownloadTask;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import org.springframework.stereotype.Service;

import static com.swmaestro.cotuber.video.ProgressState.AI_PROCESSING;
import static com.swmaestro.cotuber.video.ProgressState.YOUTUBE_DOWNLOADING;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoDownloadQueue videoDownloadQueue;
    private final AIProcessQueue aiProcessQueue;
    private final YoutubeVideoDownloader youtubeVideoDownloader;

    public VideoService(VideoRepository videoRepository, VideoDownloadQueue videoDownloadQueue,
                        AIProcessQueue aiProcessQueue, YoutubeVideoDownloader youtubeVideoDownloader) {
        this.videoRepository = videoRepository;
        this.videoDownloadQueue = videoDownloadQueue;
        this.aiProcessQueue = aiProcessQueue;
        this.youtubeVideoDownloader = youtubeVideoDownloader;
    }

    public VideoCreateResponseDto requestVideoDownload(final long userId, final VideoCreateRequestDto request) {
        final Video video = videoRepository.save(Video.initialVideo(userId, request));

        videoDownloadQueue.push(VideoDownloadTask.builder()
                .id(video.getId())
                .youtubeUrl(request.url())
                .build());

        return VideoCreateResponseDto.builder()
                .id(video.getId())
                .build();
    }

    public void downloadYoutube(final VideoDownloadTask task) {
        final String s3Url = youtubeVideoDownloader.download(task.youtubeUrl());

        final Video video = videoRepository.findById(task.id());
        video.changeS3Path(s3Url);
        video.changeState(AI_PROCESSING);

        videoRepository.save(video);
        aiProcessQueue.push(AIProcessTask.builder().build());
    }
}
