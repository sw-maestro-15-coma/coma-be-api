package com.swmaestro.cotuber.domain.video;

import com.swmaestro.cotuber.domain.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.domain.batch.dto.VideoDownloadTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.swmaestro.cotuber.domain.video.ProgressState.AI_PROCESSING;
import static com.swmaestro.cotuber.domain.video.ProgressState.YOUTUBE_DOWNLOADING;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoDownloadQueue queue;
    private final YoutubeVideoDownloader youtubeVideoDownloader;

    public VideoService(VideoRepository videoRepository, VideoDownloadQueue queue, YoutubeVideoDownloader youtubeVideoDownloader) {
        this.videoRepository = videoRepository;
        this.queue = queue;
        this.youtubeVideoDownloader = youtubeVideoDownloader;
    }

    @Transactional
    public VideoCreateResponseDto requestVideoDownload(final VideoCreateRequestDto request) {
        final long id = videoRepository.insert(createInitVideo(request));

        queue.push(VideoDownloadTask.builder()
                .id(id)
                .youtubeUrl(request.url())
                .build());

        return VideoCreateResponseDto.builder()
                .id(id)
                .build();
    }

    @Transactional
    public void downloadYoutube(final VideoDownloadTask task) {
        final String s3Url = youtubeVideoDownloader.download(task.youtubeUrl());

        final Video video = videoRepository.findById(task.id());
        video.changeS3Path(s3Url);
        video.changeState(AI_PROCESSING);

        videoRepository.update(video);
    }

    private Video createInitVideo(final VideoCreateRequestDto request) {
        return Video.builder()
                .id(0L)
                .s3Path("")
                .state(YOUTUBE_DOWNLOADING)
                .youtubeUrl(request.url())
                .build();
    }
}
