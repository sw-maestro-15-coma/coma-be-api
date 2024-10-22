package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.domain.Draft;

import java.util.List;
import java.util.Optional;

public interface DraftRepository {
    Draft save(Draft draft);

    Optional<Draft> findById(Long id);

    List<Draft> findAllByUserId(long userId);

    List<Draft> findAllByVideoId(long videoId);
}
