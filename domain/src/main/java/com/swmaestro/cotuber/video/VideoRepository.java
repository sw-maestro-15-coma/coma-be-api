package com.swmaestro.cotuber.video;

public interface VideoRepository {
    Video save(Video video);

    Video findById(long id);
}
