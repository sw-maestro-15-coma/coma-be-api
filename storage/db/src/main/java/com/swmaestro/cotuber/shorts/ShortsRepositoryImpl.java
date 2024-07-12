package com.swmaestro.cotuber.shorts;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShortsRepositoryImpl implements ShortsRepository {
    private final ShortsEntityRepository repository;

    public ShortsRepositoryImpl(ShortsEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void insert(final Shorts shorts) {
        // shorts entity에 대한 db 구조 설명 필요 -> shorts 자체에 대한 db와 shorts 처리를 위한 엔티티가 따로 필요해보임
    }

    @Override
    public List<Shorts> findAll() {
        return List.of();
    }

    @Override
    public List<Shorts> findAllByVideoId(final long videoId) {
        return List.of();
    }
}
