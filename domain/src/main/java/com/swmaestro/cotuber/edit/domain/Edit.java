package com.swmaestro.cotuber.edit.domain;

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
}
