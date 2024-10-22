package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EditService {
    private final EditRepository editRepository;
    private final EditSubtitleRepository editSubtitleRepository;

    public Edit getEdit(final Long id) {
        return editRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 edit이 없습니다"));
    }

    public List<EditSubtitle> getEditSubtitleList(final long editId) {
        return editSubtitleRepository.findAllByEditId(editId);
    }

    public Edit saveEdit(final Edit edit) {
        return editRepository.save(edit);
    }

    public void saveEditSubtitle(final List<EditSubtitle> editSubtitleList) {
        editSubtitleRepository.saveAll(editSubtitleList);
    }
}