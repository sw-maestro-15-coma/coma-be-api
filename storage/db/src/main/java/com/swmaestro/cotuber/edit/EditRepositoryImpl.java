package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.edit.domain.Edit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public class EditRepositoryImpl implements EditRepository {
    private final EditEntityRepository repository;

    public EditRepositoryImpl(EditEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Edit save(final Edit edit) {
        return repository.save(EditEntity.from(edit)).toDomain();
    }

    @Override
    public Optional<Edit> findById(final long editId) {
        return repository.findById(editId).map(EditEntity::toDomain);
    }

    @Override
    public Optional<Edit> findByDraftId(long draftId) {
        return repository.findByDraftId(draftId).map(EditEntity::toDomain);
    }
}
