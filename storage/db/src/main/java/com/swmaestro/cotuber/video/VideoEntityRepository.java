package com.swmaestro.cotuber.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VideoEntityRepository extends JpaRepository<VideoEntity, Long> {
    @Query("SELECT v FROM VideoEntity v WHERE v.youtubeUrl = :youtubeUrl")
    Optional<VideoEntity> findByYoutubeUrl(String youtubeUrl);
}
