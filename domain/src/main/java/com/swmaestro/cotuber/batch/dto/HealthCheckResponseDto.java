package com.swmaestro.cotuber.batch.dto;

public record HealthCheckResponseDto(String message) {
    public static HealthCheckResponseDto ok() {
        return new HealthCheckResponseDto("OK");
    }
}
