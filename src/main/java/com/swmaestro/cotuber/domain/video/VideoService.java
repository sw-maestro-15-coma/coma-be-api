package com.swmaestro.cotuber.domain.video;

import com.swmaestro.cotuber.domain.batch.VideoDownloadQueue;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.domain.batch.dto.VideoDownloadTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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
        final long id = videoRepository.insert(request);

        queue.push(VideoDownloadTask.builder()
                .id(id)
                .youtubeUrl(request.url())
                .build());

        return VideoCreateResponseDto.builder()
                .id(id)
                .build();
    }

    @Transactional
    public void downloadYoutube() {
        if (queue.isEmpty()) {
            return;
        }

        final VideoDownloadTask task = queue.pop();
        final String s3Url = youtubeVideoDownloader.download(task.youtubeUrl());

        videoRepository.updateS3Path(task.id(), s3Url);
    }
}
