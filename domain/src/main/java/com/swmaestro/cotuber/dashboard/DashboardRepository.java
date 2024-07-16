package com.swmaestro.cotuber.dashboard;

import java.util.List;

public interface DashboardRepository {
    List<Dashboard> findAllByUserId(long userId);
}
