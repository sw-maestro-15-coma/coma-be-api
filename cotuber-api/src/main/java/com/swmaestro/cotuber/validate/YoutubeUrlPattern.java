package com.swmaestro.cotuber.validate;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class YoutubeUrlPattern {
    private static final String YOUTUBE_URL_PATTERN =
            "^https?://(?:www\\.)?(youtube\\.com|youtu\\.be)/(watch\\?v=|embed/|v/|.+\\?v=)?([^&\\n]+)";

    private final Pattern pattern;

    public YoutubeUrlPattern() {
        pattern = Pattern.compile(YOUTUBE_URL_PATTERN);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
