package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.video.domain.VideoSubtitle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "edit_subtitle")
public class EditSubtitleEntity extends BaseEntity {
    @Column(name = "edit_id")
    private long editId;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "start")
    private int start;
    @Column(name = "end")
    private int end;

    @Builder
    public EditSubtitleEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                              long editId, String subtitle, int start, int end) {
        super(id, createdAt, updatedAt);
        this.editId = editId;
        this.subtitle = subtitle;
        this.start = start;
        this.end = end;
    }

    public EditSubtitle toDomain() {
        return EditSubtitle.builder()
                .id(getId())
                .editId(getEditId())
                .subtitle(getSubtitle())
                .start(getStart())
                .end(getEnd())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static EditSubtitleEntity from(EditSubtitle editSubtitle) {
        return EditSubtitleEntity.builder()
                .id(editSubtitle.getId())
                .editId(editSubtitle.getEditId())
                .subtitle(editSubtitle.getSubtitle())
                .start(editSubtitle.getStart())
                .end(editSubtitle.getEnd())
                .createdAt(editSubtitle.getCreatedAt())
                .updatedAt(editSubtitle.getUpdatedAt())
                .build();
    }
}
