package com.swmaestro.cotuber.domain.video;

import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.domain.video.dto.VideoDownloadTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoDownloadQueue queue;

    public VideoService(VideoRepository videoRepository, VideoDownloadQueue queue) {
        this.videoRepository = videoRepository;
        this.queue = queue;
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
}
