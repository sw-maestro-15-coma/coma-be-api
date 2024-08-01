package com.swmaestro.cotuber.dashboard;

import com.swmaestro.cotuber.shorts.ShortsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface DashboardDtoRepository extends Repository<ShortsEntity, Long> {
    @Query("""
                        SELECT new com.swmaestro.cotuber.dashboard.DashboardDto(s.id, v.title, v.youtubeUrl, s.progressState)
                        FROM ShortsEntity s
                        INNER JOIN VideoEntity v ON s.videoId = v.id
                        WHERE s.userId = :userId
            """)
    List<DashboardDto> findAllByUserId(final long userId);
}
