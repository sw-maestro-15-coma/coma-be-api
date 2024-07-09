package com.swmaestro.cotuber.infra.video;

import com.swmaestro.cotuber.domain.video.Video;
import com.swmaestro.cotuber.domain.video.VideoRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class VideoRepositoryImpl implements VideoRepository {
    private final VideoEntityRepository repository;

    public VideoRepositoryImpl(VideoEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public long insert(final Video video) {
        final VideoEntity videoEntity = VideoEntity.builder()
                .s3Path(video.getS3Path())
                .youtubeUrl(video.getYoutubeUrl())
                .state(video.getState())
                .build();

        final VideoEntity savedEntity = repository.save(videoEntity);

        return savedEntity.getId();
    }

    @Override
    public void update(final Video video) {
        final VideoEntity videoEntity = repository.findById(video.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 id의 엔티티가 없습니다."));

        videoEntity.setS3Path(video.getS3Path());
        videoEntity.setYoutubeUrl(video.getYoutubeUrl());
        videoEntity.setState(video.getState());

        repository.save(videoEntity);
    }

    @Override
    public Video findById(final long id) {
        final VideoEntity videoEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 엔티티가 없습니다."));

        return videoEntity.toDomain();
    }
}
