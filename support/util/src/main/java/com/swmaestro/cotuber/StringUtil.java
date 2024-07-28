package com.swmaestro.cotuber;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
    public static String convertToUTF8(String str) {
        return new String(str.getBytes(), StandardCharsets.UTF_8);
    }

    public static String secondToFormat(int totalSecond) {
        int hour = totalSecond / 3600;
        int minute = (totalSecond % 3600) / 60;
        int second = totalSecond % 60;

        return addZeroIfOneDigit(hour) +
                ":" +
                addZeroIfOneDigit(minute) +
                ":" +
                addZeroIfOneDigit(second);
    }

    public static String addZeroIfOneDigit(int num) {
        if (num < 10) {
            return "0" + num;
        }

        return String.valueOf(num);
    }
}
