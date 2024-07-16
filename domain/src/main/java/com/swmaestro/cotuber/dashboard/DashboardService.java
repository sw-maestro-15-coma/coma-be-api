package com.swmaestro.cotuber.dashboard;

import com.swmaestro.cotuber.dashboard.dto.DashboardListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public List<DashboardListResponseDto> getDashboard(final long userId) {
        final List<Dashboard> dashboards = dashboardRepository.findAllByUserId(userId);
        final List<DashboardListResponseDto> results = new ArrayList<>();
        dashboards.forEach(e -> results.add(
                DashboardListResponseDto.builder()
                        .shortsId(e.shortsId())
                        .title(e.title())
                        .youtubeUrl(e.youtubeUrl())
                        .progressState(e.progressState())
                        .build()
        ));
        return results;
    }
}
