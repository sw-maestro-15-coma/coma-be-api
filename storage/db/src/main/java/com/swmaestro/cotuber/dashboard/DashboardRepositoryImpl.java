package com.swmaestro.cotuber.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class DashboardRepositoryImpl implements DashboardRepository {
    private final DashboardDtoRepository dashboardDtoRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Dashboard> findAllByUserId(final long userId) {
        List<DashboardDto> results = dashboardDtoRepository.findAllByUserId(userId);

        return results.stream()
                .map(DashboardDto::toDashboard)
                .toList();
    }
}
