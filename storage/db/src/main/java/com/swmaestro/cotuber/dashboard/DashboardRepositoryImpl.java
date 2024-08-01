package com.swmaestro.cotuber.dashboard;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DashboardRepositoryImpl implements DashboardRepository {
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<Dashboard> findAllByUserId(final long userId) {
        final Query query =
                entityManager.createQuery("""
                                            SELECT new com.swmaestro.cotuber.dashboard.DashboardDto(s.id, v.title, v.youtubeUrl, s.progressState)
                                            FROM ShortsEntity s
                                            INNER JOIN VideoEntity v ON s.videoId = v.id
                                            WHERE s.userId = :userId
                                """)
                        .setParameter("userId", userId);

        final List<DashboardDto> results = (List<DashboardDto>) query.getResultList();

        return results.stream()
                .map(DashboardDto::toDashboard)
                .toList();
    }
}
