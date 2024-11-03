package com.swmaestro.cotuber.edit.domain;

import com.swmaestro.cotuber.edit.dto.EditRequestDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class Edit {
    private final long id;
    private final long draftId;
    private String title;
    private int start;
    private int end;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(EditRequestDto editRequest) {
        this.title = editRequest.title();
        this.start = editRequest.start();
        this.end = editRequest.end();
        this.updatedAt = LocalDateTime.now();
    }
}
