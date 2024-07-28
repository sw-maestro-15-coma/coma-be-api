package com.swmaestro.cotuber.dashboard;

import com.swmaestro.cotuber.dashboard.dto.DashboardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public List<DashboardListResponseDto> getDashboard(final long userId) {
        final List<Dashboard> dashboards = dashboardRepository.findAllByUserId(userId);

        return dashboards.stream()
                .map(DashboardListResponseDto::new)
                .toList();
    }
}
