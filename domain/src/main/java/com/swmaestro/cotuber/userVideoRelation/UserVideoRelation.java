package com.swmaestro.cotuber.userVideoRelation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.swmaestro.cotuber.shorts.ShortsStatus.ERROR;
import static com.swmaestro.cotuber.shorts.ShortsStatus.GENERATING;

@Builder
@Getter
public class UserVideoRelation {
    private final long id;
    private final long userId;
    private final long videoId;
    private UserVideoRelationStatus userVideoRelationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
