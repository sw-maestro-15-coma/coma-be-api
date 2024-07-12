package com.swmaestro.cotuber.shorts;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class ShortsRepositoryImpl implements ShortsRepository {
    private final ShortsEntityRepository repository;

    public ShortsRepositoryImpl(ShortsEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Shorts save(final Shorts shorts) {
        final ShortsEntity savedShorts = repository.save(ShortsEntity.from(shorts));
        return savedShorts.toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Shorts> findAll() {
        final List<ShortsEntity> entities = repository.findAll();
        final List<Shorts> results = new ArrayList<>();
        entities.forEach(e -> results.add(e.toDomain()));

        return results;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Shorts> findAllByVideoId(final long videoId) {
        final List<ShortsEntity> entities = repository.findAllByVideoId(videoId);
        final List<Shorts> results = new ArrayList<>();
        entities.forEach(e -> results.add(e.toDomain()));

        return results;
    }
}
