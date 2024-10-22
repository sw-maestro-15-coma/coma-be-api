package com.swmaestro.cotuber.health.dto;

public record HealthCheckResponseDto(String message) {
    public static HealthCheckResponseDto ok() {
        return new HealthCheckResponseDto("OK");
    }
}
