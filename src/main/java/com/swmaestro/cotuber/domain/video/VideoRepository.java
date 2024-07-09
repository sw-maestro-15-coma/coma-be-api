package com.swmaestro.cotuber.domain.video;

public interface VideoRepository {
    long insert(Video video);
    void update(Video video);
    Video findById(long id);
}
