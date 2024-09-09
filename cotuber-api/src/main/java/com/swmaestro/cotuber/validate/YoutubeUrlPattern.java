package com.swmaestro.cotuber.validate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class YoutubeUrlPattern {
    private static final Pattern YOUTUBE_URL_PATTERN =
            Pattern.compile("^https?://(?:www\\.)?(youtube\\.com|youtu\\.be)/(watch\\?v=|embed/|v/|.+\\?v=)?([^&\\n]+)");

    public static Pattern getPattern() {
        return YOUTUBE_URL_PATTERN;
    }
}
