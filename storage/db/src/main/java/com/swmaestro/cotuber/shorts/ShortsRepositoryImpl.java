package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.domain.Shorts;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class ShortsRepositoryImpl implements ShortsRepository {
    private final ShortsEntityRepository repository;

    public ShortsRepositoryImpl(ShortsEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shorts save(final Shorts shorts) {
        return repository.save(ShortsEntity.from(shorts)).toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Shorts> findById(long shortsId) {
        return repository.findById(shortsId).map(ShortsEntity::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Shorts> findAllByUserId(long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(ShortsEntity::toDomain).toList();
    }
}
