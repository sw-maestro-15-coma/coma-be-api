package com.swmaestro.cotuber.video;

import java.net.URI;

public class YoutubeEmbedUrlParseKeyStrategy implements YoutubeUrlParseKeyStrategy {
    private static final String YOUTUBE_EMBED_URL_HOST = "youtube.com/embed";

    @Override
    public boolean isApplicable(URI youtubeUrl) {
        return youtubeUrl.toString().contains(YOUTUBE_EMBED_URL_HOST);
    }

    @Override
    public String getKey(URI youtubeUrl) {
        String path = youtubeUrl.getPath();
        String key = path.substring("/embed".length());

        if (key.isBlank() || key.equals("/")) {
            throw new IllegalArgumentException("유효하지 않은 URL입니다");
        }

        return key.substring(1);
    }
}
