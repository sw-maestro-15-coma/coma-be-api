package com.swmaestro.cotuber.video;

import java.net.URI;

public class YoutubeShareUrlParseKeyStrategy implements YoutubeUrlParseKeyStrategy {
    private static final String YOUTUBE_SHORT_URL_HOST = "youtu.be";

    @Override
    public boolean isApplicable(URI youtubeUrl) {
        return YOUTUBE_SHORT_URL_HOST.equals(youtubeUrl.getHost());
    }

    @Override
    public String getKey(URI youtubeUrl) {
        String path = youtubeUrl.getPath();

        if (path == null || path.length() < 2) {
            throw new IllegalArgumentException("유효하지 않은 URL입니다");
        }

        return path.substring(1);
    }
}
