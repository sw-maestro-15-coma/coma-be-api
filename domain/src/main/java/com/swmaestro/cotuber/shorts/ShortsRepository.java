package com.swmaestro.cotuber.shorts;

import java.util.List;

public interface ShortsRepository {
    Shorts save(Shorts shorts);

    List<Shorts> findAll();

    List<Shorts> findAllByVideoId(long videoId);
}
