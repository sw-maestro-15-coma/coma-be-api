package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.edit.domain.Edit;

import java.util.Optional;

public interface EditRepository {
    Edit save(Edit edit);

    Optional<Edit> findById(long editId);
}
