package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.Video;

import java.util.Optional;

public interface VideoRepository {
    Video save(Video video);

    Optional<Video> findById(long id);

    Optional<Video> findByYoutubeUrl(String youtubeUrl);
}
