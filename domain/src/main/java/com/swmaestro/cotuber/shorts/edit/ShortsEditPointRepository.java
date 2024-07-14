package com.swmaestro.cotuber.shorts.edit;

import java.util.Optional;

public interface ShortsEditPointRepository {
    ShortsEditPoint save(ShortsEditPoint editPoint);
    Optional<ShortsEditPoint> findById(long shortsEditPointId);
}
