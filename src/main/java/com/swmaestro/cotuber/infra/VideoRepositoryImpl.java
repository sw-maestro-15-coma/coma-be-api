package com.swmaestro.cotuber.infra;

import com.swmaestro.cotuber.domain.video.Video;
import com.swmaestro.cotuber.domain.video.VideoEntity;
import com.swmaestro.cotuber.domain.video.VideoRepository;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public class VideoRepositoryImpl implements VideoRepository {
    private static final String EMPTY_URL = "";

    private final VideoEntityRepository repository;

    public VideoRepositoryImpl(VideoEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public long insert(final VideoCreateRequestDto request) {
        final VideoEntity videoEntity = VideoEntity.builder()
                .s3Path(EMPTY_URL)
                .youtubeUrl(request.url())
                .build();

        final VideoEntity savedEntity = repository.save(videoEntity);

        return savedEntity.getId();
    }

    @Override
    public void updateS3Path(final long id, final String s3Path) {
        final VideoEntity videoEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 엔티티가 없습니다."));

        videoEntity.updateS3Path(s3Path);
        repository.save(videoEntity);
    }

    @Override
    public Video findById(long id) {
        final VideoEntity videoEntity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 엔티티가 없습니다."));

        return videoEntity.toDomain();
    }
}
