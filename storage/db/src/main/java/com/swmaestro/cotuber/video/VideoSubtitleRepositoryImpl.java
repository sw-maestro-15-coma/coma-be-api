package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.edit.EditSubtitleEntity;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class VideoSubtitleRepositoryImpl implements VideoSubtitleRepository {
    private final VideoSubtitleEntityRepository repository;

    public VideoSubtitleRepositoryImpl(VideoSubtitleEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VideoSubtitle> saveAll(List<VideoSubtitle> subtitleList) {
        List<VideoSubtitleEntity> subtitleListEntity = subtitleList.stream().map(VideoSubtitleEntity::from).toList();
        return repository.saveAll(subtitleListEntity).stream().map(VideoSubtitleEntity::toDomain).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<VideoSubtitle> findAllByVideoId(long videoId) {
        return repository.findAllByVideoId(videoId).stream().map(VideoSubtitleEntity::toDomain).toList();
    }
}
