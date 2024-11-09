package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EditService {
    private final EditRepository editRepository;
    private final EditSubtitleRepository editSubtitleRepository;

    public Optional<Edit> findEdit(final long editId) {
        return editRepository.findById(editId);
    }

    public Optional<Edit> findByDraftId(final long draftId) {
        return editRepository.findByDraftId(draftId);
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