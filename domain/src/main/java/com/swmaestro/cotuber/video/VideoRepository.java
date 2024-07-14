package com.swmaestro.cotuber.video;

import java.util.Optional;

public interface VideoRepository {
    Video save(Video video);

    Optional<Video> findById(long id);
}
