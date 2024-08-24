package com.swmaestro.cotuber.video;

import java.net.URI;

public interface YoutubeUrlParseKeyStrategy {
    boolean isApplicable(URI youtubeUrl);

    String getKey(URI youtubeUrl);
}
