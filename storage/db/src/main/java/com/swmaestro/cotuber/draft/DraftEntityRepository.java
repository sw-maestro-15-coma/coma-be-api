package com.swmaestro.cotuber.draft;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DraftEntityRepository extends JpaRepository<DraftEntity, Long> {
    List<DraftEntity> findAllByUserId(long userId);
    List<DraftEntity> findAllByVideoId(long videoId);
}
