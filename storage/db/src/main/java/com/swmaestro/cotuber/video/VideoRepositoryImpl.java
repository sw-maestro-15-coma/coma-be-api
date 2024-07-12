package com.swmaestro.cotuber.video;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Transactional
@Repository
public class VideoRepositoryImpl implements VideoRepository {
    private final VideoEntityRepository repository;

    public VideoRepositoryImpl(VideoEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Video save(final Video video) {
        final VideoEntity savedEntity = repository.save(VideoEntity.from(video));

        return savedEntity.toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Video findById(final long id) {
        final VideoEntity videoEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 엔티티가 없습니다."));

        return videoEntity.toDomain();
    }
}
