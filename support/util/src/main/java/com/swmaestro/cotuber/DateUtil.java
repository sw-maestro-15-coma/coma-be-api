package com.swmaestro.cotuber;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {
    public static LocalDateTime toLocalDateTime(long timestamp) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId()
        );
    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(TimeZone.getDefault().toZoneId()).toInstant().toEpochMilli();
    }
}
