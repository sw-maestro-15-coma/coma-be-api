package com.swmaestro.cotuber.edit.domain;

import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.edit.dto.EditSubtitleBaseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class EditSubtitle {
    private final long id;
    private final long editId;
    private String subtitle;
    private int start;
    private int end;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EditSubtitle from(Draft draft, EditSubtitleBaseDto subtitle) {
        return EditSubtitle.builder()
                .id(subtitle.id())
                .editId(draft.getEditId())
                .subtitle(subtitle.subtitle())
                .start(subtitle.start())
                .end(subtitle.end())
                .build();
    }
}
