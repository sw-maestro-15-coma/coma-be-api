package com.swmaestro.cotuber.userVideoRelation;

import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsEntity;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class UserVideoRelationRepositoryImpl implements UserVideoRelationRepository {
    private final UserVideoRelationEntityRepository repository;

    public UserVideoRelationRepositoryImpl(UserVideoRelationEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserVideoRelation save(final UserVideoRelation userVideoRelation) {
        return repository.save(UserVideoRelationEntity.from(userVideoRelation)).toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserVideoRelation> findByUserIdAndVideoId(long userId, long videoId) {
        return repository.findByUserIdAndVideoId(userId, videoId)
                .map(UserVideoRelationEntity::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserVideoRelation> findAllByUserId(long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(UserVideoRelationEntity::toDomain).toList();
    }
}
