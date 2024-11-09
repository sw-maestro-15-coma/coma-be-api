package com.swmaestro.cotuber.edit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EditEntityRepository extends JpaRepository<EditEntity, Long> {
    @Query("""
            SELECT e
              FROM EditEntity e
              WHERE e.draftId = :draftId
            """)
    Optional<EditEntity> findByDraftId(long draftId);
}