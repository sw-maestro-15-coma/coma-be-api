package com.swmaestro.cotuber.infra;

import com.swmaestro.cotuber.domain.video.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoEntityRepository extends JpaRepository<VideoEntity, Long> {

}
