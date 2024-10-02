package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.domain.VideoStatus;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageRequest;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final VideoDownloadProducer videoDownloadProducer;
    private final VideoSubtitleRepository videoSubtitleRepository;
    private final VideoSubtitleGenerateProducer videoSubtitleGenerateProducer;

    public Video getVideo(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));
    }

    // 에러 발생 가능성 (youtubeUrl 동일한 데이터가 2개 이상 있을 경우?)
    public Optional<Video> findVideoByYoutubeUrl(final String youtubeUrl) {
        return videoRepository.findByYoutubeUrl(youtubeUrl);
    }

    public Video startVideoDownload(final String youtubeUrl) {
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

    public void completeVideoDownload(VideoDownloadMessageResponse response) {
        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));

        video.completeVideoDownloading(response);
        videoRepository.save(video);
    }

    public void startVideoSubtitleGenerate(final long videoId) {
        videoSubtitleGenerateProducer.send(
                VideoSubtitleGenerateMessageRequest.builder()
                        .videoId(videoId)
                        .build()
        );
    }

    public void completeVideoSubtitleGenerate(VideoSubtitleGenerateMessageResponse response) {
        videoSubtitleRepository.saveAll(response.subtitleList());
    }
}
