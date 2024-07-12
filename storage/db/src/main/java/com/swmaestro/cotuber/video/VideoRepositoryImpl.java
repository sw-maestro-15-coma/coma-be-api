package com.swmaestro.cotuber.video;

import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class VideoRepositoryImpl implements VideoRepository {
    private final VideoEntityRepository repository;

    public VideoRepositoryImpl(VideoEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public long save(final Video video) {
        final VideoEntity videoEntity = VideoEntity.builder()
                .s3Path(video.getS3Path())
                .youtubeUrl(video.getYoutubeUrl())
                .state(video.getState())
                .build();

        final VideoEntity savedEntity = repository.save(videoEntity);

        return savedEntity.getId();
    }

    @Override
    public Video findById(final long id) {
        final VideoEntity videoEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 엔티티가 없습니다."));

        return videoEntity.toDomain();
    }
}
