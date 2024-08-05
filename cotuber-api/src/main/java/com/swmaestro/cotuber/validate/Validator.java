package com.swmaestro.cotuber.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;

@RequiredArgsConstructor
@Component
public class Validator {
    private final YoutubeUrlPattern youtubeUrlPattern;

    public void checkYoutubeUrl(String youtubeUrl) {
        Objects.requireNonNull(youtubeUrl, "youtube url이 비어있습니다");
        Matcher matcher = youtubeUrlPattern.getPattern().matcher(youtubeUrl);

        if (!matcher.matches()) {
            throw new IllegalArgumentException("올바르지 않은 youtube url입니다");
        }
    }
}
