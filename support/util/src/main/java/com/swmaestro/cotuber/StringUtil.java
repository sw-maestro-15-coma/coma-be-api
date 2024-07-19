package com.swmaestro.cotuber;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {
    public static String convertToUTF8(String str) {
        return new String(str.getBytes(), StandardCharsets.UTF_8);
    }
}
