package com.swmaestro.cotuber.edit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditSubtitleEntityRepository extends JpaRepository<EditSubtitleEntity, Long> {
    List<EditSubtitleEntity> findAllByEditId(long editId);
}
