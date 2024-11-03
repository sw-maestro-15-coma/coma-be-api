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
        Draft draft = draftService.getDraft(draftId);
        editService.saveEdit(
                Edit.builder()
                        .id(draft.getEditId())
                        .title(editRequestDto.title())
                        .start(editRequestDto.start())
                        .end(editRequestDto.end())
                        .build()
        );
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
