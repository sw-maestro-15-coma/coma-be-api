package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.domain.VideoStatus;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<VideoSubtitle> getVideoSubtitleList(long videoId) {
        return videoSubtitleRepository.findAllByVideoId(videoId);
    }

    // 에러 발생 가능성 (youtubeUrl 동일한 데이터가 2개 이상 있을 경우?)
    public Optional<Video> findVideoByYoutubeUrl(final String youtubeUrl) {
        return videoRepository.findByYoutubeUrl(youtubeUrl);
    }

    public Video createVideo(final String youtubeUrl) {
        return videoRepository.save(
                Video.builder()
                        .youtubeUrl(youtubeUrl)
                        .s3Url(null)
                        .title(null)
                        .videoTotalSecond(0)
                        .videoStatus(VideoStatus.VIDEO_DOWNLOADING)
                        .build()
        );
    }

    public Video updateVideoStatus(final long videoId, final VideoStatus videoStatus) {
        Video video = getVideo(videoId);
        video.changeVideoStatus(videoStatus);
        return videoRepository.save(video);
    }

    public void startVideoDownload(Video video) {
        videoDownloadProducer.send(
                VideoDownloadMessageRequest.builder()
                        .videoId(video.getId())
                        .youtubeUrl(video.getYoutubeUrl())
                        .build()
        );
    }

    public void completeVideoDownload(VideoDownloadMessageResponse response) {
        Video video = videoRepository.findById(response.videoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 video가 없습니다"));

        video.completeVideoDownloading(response);
        videoRepository.save(video);
    }

    public void startVideoSubtitleGenerate(final long videoId, String s3Url) {
        videoSubtitleGenerateProducer.send(
                VideoSubtitleGenerateMessageRequest.builder()
                        .videoId(videoId)
                        .s3Url(s3Url)
                        .build()
        );
    }

    public void saveVideoSubtitles(List<VideoSubtitle> videoSubtitles) {
        videoSubtitleRepository.saveAll(videoSubtitles);
    }
}
