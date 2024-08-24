package com.swmaestro.cotuber.video;

import lombok.Getter;

import java.net.URI;
import java.util.Set;

public class YoutubeUrl {
    private static final Set<YoutubeUrlParseKeyStrategy> strategies = Set.of(
            new YoutubeWatchUrlParseKeyStrategy(),
            new YoutubeShareUrlParseKeyStrategy(),
            new YoutubeEmbedUrlParseKeyStrategy()
    );

    private final URI url;

    @Getter
    private final String key;

    public YoutubeUrl(String url) {
        validateUrl(url);
        this.url = URI.create(url);
        this.key = findStrategy(this.url).getKey(this.url);
    }

    private void validateUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("url이 비어있습니다.");
        }
    }

    private YoutubeUrlParseKeyStrategy findStrategy(URI uri) {
        return strategies.stream()
                .filter(it -> it.isApplicable(uri))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Youtube의 URL이 아닙니다"));
    }

    public String getUrlString() {
        return this.url.toString();
    }
}
