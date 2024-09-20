package com.swmaestro.cotuber.userVideoRelation;

import java.util.List;
import java.util.Optional;

public interface UserVideoRelationRepository {
    UserVideoRelation save(UserVideoRelation userVideoRelation);

    Optional<UserVideoRelation> findByUserIdAndVideoId(long userId, long videoId);

    List<UserVideoRelation> findAllByUserId(long userId);
}
