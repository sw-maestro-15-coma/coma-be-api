package com.swmaestro.cotuber.video;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class YoutubeWatchUrlParseKeyStrategy implements YoutubeUrlParseKeyStrategy {
    private static final String YOUTUBE_WATCH_URL_HOST = "youtube.com/watch";

    @Override
    public boolean isApplicable(URI youtubeUrl) {
        return youtubeUrl.toString().contains(YOUTUBE_WATCH_URL_HOST);
    }

    @Override
    public String getKey(URI youtubeUrl) {
        Map<String, String> params = getQueryParams(youtubeUrl);

        if (!params.containsKey("v")) {
            throw new IllegalArgumentException("올바른 Youtube url이 아닙니다");
        }

        return params.get("v");
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
}
