package com.swmaestro.cotuber.domain.shorts;

import java.util.List;

public interface ShortsRepository {
    void insert(Shorts shorts);
    List<Shorts> findAll();
    List<Shorts> findAllByVideoId(long videoId);
}
