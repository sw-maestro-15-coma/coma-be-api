package com.swmaestro.cotuber.shorts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortsEntityRepository extends JpaRepository<ShortsEntity, Long> {
    List<ShortsEntity> findAllByVideoId(long videoId);
}
