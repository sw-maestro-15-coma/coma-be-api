package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {
    private final AfterVideoDownloadService afterVideoDownloadService;
    private final VideoRepository videoRepository;
    private final VideoDownloadProducer videoDownloadProducer;

    public Video getVideoByYoutubeUrl(final String youtubeUrl) {
        return videoRepository.findByYoutubeUrl(youtubeUrl).orElseThrow();
    }

    public Video requestVideoDownload(final String youtubeUrl) {
        Video newVideo = videoRepository.save(
                Video.builder()
                        .youtubeUrl(youtubeUrl)
                        .s3Url(null)
                        .title(null)
                        .videoTotalSecond(0)
                        .videoStatus(VideoStatus.VIDEO_DOWNLOADING)
                        .build()
        );

        videoDownloadProducer.send(
                VideoDownloadMessageRequest.builder()
                        .videoId(newVideo.getId())
                        .youtubeUrl(youtubeUrl)
                        .build()
        );

        return newVideo;
    }
}
