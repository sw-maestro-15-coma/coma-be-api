package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.edit.domain.EditSubtitle;

import java.util.List;
import java.util.Optional;

public interface EditSubtitleRepository {
    List<EditSubtitle> saveAll(List<EditSubtitle> subtitleList);

    List<EditSubtitle> findAllByEditId(long editId);
}
