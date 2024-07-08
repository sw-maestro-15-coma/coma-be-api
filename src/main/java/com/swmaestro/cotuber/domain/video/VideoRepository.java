package com.swmaestro.cotuber.domain.video;

import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;

public interface VideoRepository {
    long insert(VideoCreateRequestDto request);
    void update(Video video);
    Video findById(long id);
}
