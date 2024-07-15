package com.swmaestro.cotuber.shorts;

import java.util.List;
import java.util.Optional;

public interface ShortsRepository {
    Shorts save(Shorts shorts);

    Optional<Shorts> findById(long shortsId);

    List<Shorts> findAllByUserId(long userId);
}
