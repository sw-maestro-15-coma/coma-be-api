package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.video.domain.EditSubtitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditSubtitleEntityRepository extends JpaRepository<EditSubtitle, Long> {
    List<EditSubtitle> findAllByVideoId(long videoId);
}
