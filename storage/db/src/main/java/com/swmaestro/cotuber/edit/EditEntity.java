package com.swmaestro.cotuber.edit;

import com.swmaestro.cotuber.common.BaseEntity;
import com.swmaestro.cotuber.edit.domain.Edit;
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
@Table(name = "edit")
public class EditEntity extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "start")
    private int start;
    @Column(name = "end")
    private int end;

    @Builder
    public EditEntity(long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                      long draftId, String title, int start, int end) {
        super(id, createdAt, updatedAt);
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Edit toDomain() {
        return Edit.builder()
                .id(getId())
                .title(title)
                .start(start)
                .end(end)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static EditEntity from(final Edit edit) {
        return EditEntity.builder()
                .id(edit.getId())
                .title(edit.getTitle())
                .start(edit.getStart())
                .end(edit.getEnd())
                .createdAt(edit.getCreatedAt())
                .updatedAt(edit.getUpdatedAt())
                .build();
    }
}
