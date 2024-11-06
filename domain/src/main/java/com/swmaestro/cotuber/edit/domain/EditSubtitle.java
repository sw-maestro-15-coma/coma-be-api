package com.swmaestro.cotuber.edit.domain;

import com.swmaestro.cotuber.edit.dto.EditSubtitleBaseDto;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
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

    public static EditSubtitle from(Edit edit, EditSubtitleBaseDto subtitle) {
        return EditSubtitle.builder()
                .id(subtitle.id())
                .editId(edit.getId())
                .subtitle(subtitle.subtitle())
                .start(subtitle.start())
                .end(subtitle.end())
                .build();
    }

    public static EditSubtitle from(long editId, VideoSubtitle videoSubtitle) {
        return EditSubtitle.builder()
                .editId(editId)
                .subtitle(videoSubtitle.getSubtitle())
                .start(videoSubtitle.getStart())
                .end(videoSubtitle.getEnd())
                .build();
    }
}
