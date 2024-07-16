package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoDownloadResponse;

public interface YoutubeVideoDownloader {
    VideoDownloadResponse download(String youtubeUrl);
}
