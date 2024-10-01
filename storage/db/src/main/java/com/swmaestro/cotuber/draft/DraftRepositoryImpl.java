package com.swmaestro.cotuber.draft;

import com.swmaestro.cotuber.draft.domain.Draft;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class DraftRepositoryImpl implements DraftRepository {
    private final DraftEntityRepository repository;

    public DraftRepositoryImpl(DraftEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Draft save(final Draft draft) {
        return repository.save(DraftEntity.from(draft)).toDomain();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Draft> findById(final Long id) {
        return repository.findById(id)
                .map(DraftEntity::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Draft> findAllByUserId(long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(DraftEntity::toDomain).toList();
    }


    @Transactional(readOnly = true)
    @Override
    public List<Draft> findAllByVideoId(long videoId) {
        return repository.findAllByVideoId(videoId).stream()
                .map(DraftEntity::toDomain).toList();
    }
}
