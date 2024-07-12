package com.swmaestro.cotuber.video;

public interface VideoRepository {
    long save(Video video);

    Video findById(long id);
}
