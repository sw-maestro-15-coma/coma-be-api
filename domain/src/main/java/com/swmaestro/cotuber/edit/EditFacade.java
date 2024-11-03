package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.edit.dto.EditRequestDto;
import com.swmaestro.cotuber.edit.dto.EditSubtitleUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class EditFacade {
    private final DraftService draftService;
    private final EditService editService;

    public void updateEdit(final long draftId, final EditRequestDto editRequestDto) {
        Edit edit = editService.findByDraftId(draftId)
                .orElseThrow(() -> new IllegalArgumentException("해당 draft에 대한 edit이 없습니다"));

        edit.update(editRequestDto);
        editService.saveEdit(edit);
    }

    public void updateEditSubtitle(final long draftId, final EditSubtitleUpdateRequestDto updateRequest) {
        Draft draft = draftService.getDraft(draftId);

        List<EditSubtitle> editSubtitles = updateRequest.subtitleList()
                .stream()
                .map(subtitle -> EditSubtitle.from(draft, subtitle))
                .toList();

        editService.saveEditSubtitle(editSubtitles);
    }
}
