package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.VideoSubtitle;

import java.util.List;

public interface VideoSubtitleRepository {
    List<VideoSubtitle> saveAll(List<VideoSubtitle> subtitleList);

    List<VideoSubtitle> findAllByVideoId(long videoId);
}
