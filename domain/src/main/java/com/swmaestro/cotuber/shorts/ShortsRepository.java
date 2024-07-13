package com.swmaestro.cotuber.shorts;

import java.util.List;

public interface ShortsRepository {
    Shorts save(Shorts shorts);

    Shorts findById(long shortsId);

    List<Shorts> findAll();

    List<Shorts> findAllByVideoId(long videoId);
}
