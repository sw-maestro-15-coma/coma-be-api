package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.video.domain.Video;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import com.swmaestro.cotuber.video.dto.VideoSubtitleGenerateMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class VideoFacade {
    private final VideoService videoService;
    private final DraftService draftService;

    public void afterVideoDownload(final VideoDownloadMessageResponse response) {
        videoService.completeVideoDownload(response);
        videoService.startVideoSubtitleGenerate(response.videoId(), response.s3Url());
        draftService.updateDraftStatusToVideoSubtitleGenerateByVideoId(response.videoId());
    }

    public void afterVideoSubtitleGenerate(final VideoSubtitleGenerateMessageResponse response) {
        Video video = videoService.completeSubtitleGenerate(response.videoId());
        List<VideoSubtitle> videoSubtitles = saveVideoSubtitles(video, response);

        draftService.startAIProcessByVideoId(response.videoId(), videoSubtitles);
    }

    private List<VideoSubtitle> saveVideoSubtitles(Video video, VideoSubtitleGenerateMessageResponse response) {
        List<VideoSubtitle> videoSubtitles = response.subtitleList()
                .stream()
                .map(subtitle -> VideoSubtitle.from(video, subtitle))
                .toList();

        videoService.saveVideoSubtitles(videoSubtitles);
        return videoSubtitles;
    }
}
