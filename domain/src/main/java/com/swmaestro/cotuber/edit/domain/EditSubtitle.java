package com.swmaestro.cotuber.edit.domain;

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
}
