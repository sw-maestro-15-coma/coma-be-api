package com.swmaestro.cotuber.video;

import lombok.Getter;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class YoutubeUrl {
    private static final String YOUTUBE_WATCH_URL_HOST = "youtube.com/watch";
    private static final String YOUTUBE_SHORT_URL_HOST = "youtu.be";
    private static final String YOUTUBE_EMBED_URL_HOST = "youtube.com/embed";

    private static final IllegalArgumentException EMPTY_URL = new IllegalArgumentException("url이 비어있습니다.");
    private static final IllegalArgumentException INVALID_YOUTUBE_URL_EXCEPTION = new IllegalArgumentException("유효하지 않은 URL입니다");
    private static final IllegalArgumentException NOT_YOUTUBE_URL_EXCEPTION = new IllegalArgumentException("Youtube의 URL이 아닙니다");

    private final String key;

    public YoutubeUrl(String url) {
        validateUrl(url);
        this.key = extractKey(URI.create(url));
    }

    private void validateUrl(String url) {
        if (url == null || url.isEmpty()) {
            throw EMPTY_URL;
        }

        if (!url.contains(YOUTUBE_WATCH_URL_HOST) && !url.contains(YOUTUBE_SHORT_URL_HOST) && !url.contains(YOUTUBE_EMBED_URL_HOST)) {
            throw INVALID_YOUTUBE_URL_EXCEPTION;
        }
    }

    private String extractKey(URI uri) {
        String extractedKey = parseKey(uri);

        if (extractedKey == null || extractedKey.isBlank()) {
            throw INVALID_YOUTUBE_URL_EXCEPTION;
        }

        return extractedKey;
    }

    private String parseKey(URI url) {
        if (url.toString().contains(YOUTUBE_WATCH_URL_HOST)) {
            return getQueryParams(url).get("v");
        }

        if (url.getHost().equals(YOUTUBE_SHORT_URL_HOST)) {
            String path = url.getPath();

            if (path == null || path.length() < 2) {
                throw INVALID_YOUTUBE_URL_EXCEPTION;
            }

            return path.substring(1);
        }

        if (url.toString().contains(YOUTUBE_EMBED_URL_HOST)) {
            String path = url.getPath();
            String key = path.substring("/embed".length());

            if (key.isBlank() || key.equals("/")) {
                throw new IllegalArgumentException("유효하지 않은 URL입니다");
            }

            return key.substring(1);
        }

        throw NOT_YOUTUBE_URL_EXCEPTION;
    }

    private Map<String, String> getQueryParams(URI url) {
        String queryParams = url.getQuery();

        if (queryParams == null) {
            return Collections.emptyMap();
        }

        String[] pairs = queryParams.split("&");

        return Arrays.stream(pairs)
                .map(pair -> pair.split("="))
                .filter(it -> it.length == 2)
                .collect(Collectors.toMap(
                        param -> URLDecoder.decode(param[0], StandardCharsets.UTF_8),
                        param -> URLDecoder.decode(param[1], StandardCharsets.UTF_8)
                ));
    }

    public String getUrlString() {
        return "https://youtube.com/watch?v=" + key;
    }
}
