package com.swmaestro.cotuber.userVideoRelation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserVideoRelationEntityRepository extends JpaRepository<UserVideoRelationEntity, Long> {
    List<UserVideoRelationEntity> findAllByUserId(long userId);
    Optional<UserVideoRelationEntity> findByUserIdAndVideoId(long userId, long videoId);
}
