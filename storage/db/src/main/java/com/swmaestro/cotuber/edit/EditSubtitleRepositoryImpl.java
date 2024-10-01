package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class EditSubtitleRepositoryImpl implements EditSubtitleRepository {
    private final EditSubtitleEntityRepository repository;

    public EditSubtitleRepositoryImpl(EditSubtitleEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EditSubtitle> saveAll(List<EditSubtitle> subtitleList) {
        List<EditSubtitleEntity> subtitleListEntity = subtitleList.stream().map(EditSubtitleEntity::from).toList();
        return repository.saveAll(subtitleListEntity).stream().map(EditSubtitleEntity::toDomain).toList();
    }

    @Override
    public List<EditSubtitle> findAllByEditId(long editId) {
        return repository.findAllByEditId(editId).stream().map(EditSubtitleEntity::toDomain).toList();
    }
}
