package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.Video;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public class VideoRepositoryImpl implements VideoRepository {
    private final VideoEntityRepository repository;

    public VideoRepositoryImpl(VideoEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Video save(final Video video) {
        return repository.save(VideoEntity.from(video)).toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Video> findById(final long id) {
        return repository.findById(id).map(VideoEntity::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Video> findByYoutubeUrl(String youtubeUrl) {
        return repository.findByYoutubeUrl(youtubeUrl).map(VideoEntity::toDomain);
    }
}
