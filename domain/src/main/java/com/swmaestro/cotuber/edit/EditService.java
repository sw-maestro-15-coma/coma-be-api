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

    public Edit saveEdit(final Edit edit) {
        return editRepository.save(edit);
    }

    public void saveEditSubtitle(final List<EditSubtitle> editSubtitleList) {
        editSubtitleRepository.saveAll(editSubtitleList);
    }
}