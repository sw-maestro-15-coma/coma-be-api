package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AfterVideoDownloadService {
    private final VideoRepository videoRepository;

    public void postProcess(VideoDownloadMessageResponse response) {
        final long videoId = 0L;

        Video video = videoRepository.findById(videoId)
                .orElseThrow();

        video.changeS3Url();

        videoRepository.save(video);
    }
}
