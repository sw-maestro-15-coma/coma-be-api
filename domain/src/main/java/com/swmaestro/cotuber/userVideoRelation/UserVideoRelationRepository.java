package com.swmaestro.cotuber.userVideoRelation;

import com.swmaestro.cotuber.userVideoRelation.domain.UserVideoRelation;

import java.util.List;
import java.util.Optional;

public interface UserVideoRelationRepository {
    UserVideoRelation save(UserVideoRelation userVideoRelation);

    Optional<UserVideoRelation> findById(Long id);

    List<UserVideoRelation> findAllByUserId(long userId);

    List<UserVideoRelation> findAllByVideoId(long videoId);
}
