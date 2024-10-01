package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.EditSubtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoSubtitleEntityRepository extends JpaRepository<EditSubtitle, Long> {
    List<EditSubtitle> findAllByVideoId(long videoId);

}
