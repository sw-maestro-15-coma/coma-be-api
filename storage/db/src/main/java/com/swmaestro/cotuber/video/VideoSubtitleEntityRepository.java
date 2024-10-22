package com.swmaestro.cotuber.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoSubtitleEntityRepository extends JpaRepository<VideoSubtitleEntity, Long> {
    List<VideoSubtitleEntity> findAllByVideoId(long videoId);
}
