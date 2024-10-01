package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.video.VideoSubtitleRepository;
import com.swmaestro.cotuber.video.domain.EditSubtitle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class EditSubtitleRepositoryImpl implements VideoSubtitleRepository {
    private final EditSubtitleEntityRepository repository;

    public EditSubtitleRepositoryImpl(EditSubtitleEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EditSubtitle> saveAll(List<EditSubtitle> subtitleList) {
        return repository.saveAll(subtitleList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EditSubtitle> findByVideoId(long videoId) {
        return repository.findAllByVideoId(videoId);
    }
}
