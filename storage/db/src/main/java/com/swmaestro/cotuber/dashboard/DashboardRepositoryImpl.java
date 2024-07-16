package com.swmaestro.cotuber.dashboard;

import com.swmaestro.cotuber.shorts.ProgressState;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class DashboardRepositoryImpl implements DashboardRepository {
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public List<Dashboard> findAllByUserId(final long userId) {
        final Query query =
                entityManager.createQuery("SELECT s.id, v.title, v.youtubeUrl, s.progressState " +
                "FROM ShortsEntity s INNER JOIN VideoEntity v ON s.videoId = v.id " +
                "WHERE s.userId = :userId");
        query.setParameter("userId", userId);

        final List<Object[]> results = (List<Object[]>) query.getResultList();
        final List<Dashboard> dashboards = new ArrayList<>();

        results.forEach(row -> dashboards.add(
                Dashboard.builder()
                        .shortsId((Long)row[0])
                        .title((String)row[1])
                        .youtubeUrl((String)row[2])
                        .progressState((ProgressState)row[3])
                        .build()
        ));

        return dashboards;
    }
}
