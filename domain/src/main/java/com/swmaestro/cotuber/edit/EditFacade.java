package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.edit.dto.EditRequestDto;
import com.swmaestro.cotuber.edit.dto.EditSubtitleUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public void updateEditSubtitle(final long draftId, final EditSubtitleUpdateRequestDto editSubtitleUpdateRequestDto) {
        Draft draft = draftService.getDraft(draftId);
        editService.saveEditSubtitle(
                editSubtitleUpdateRequestDto.subtitleList().stream()
                        .map(
                            subtitle -> EditSubtitle.builder()
                                    .id(subtitle.id())
                                    .editId(draft.getEditId())
                                    .subtitle(subtitle.subtitle())
                                    .start(subtitle.start())
                                    .end(subtitle.end())
                                    .build()
                        )
                        .toList()
        );
    }
}
