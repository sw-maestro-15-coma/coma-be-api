package com.swmaestro.cotuber.edit.dto;

public record EditRequestDto(
        String title,
        int start,
        int end
) {
}
