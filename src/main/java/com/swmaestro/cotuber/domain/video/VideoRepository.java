package com.swmaestro.cotuber.domain.video;

import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;

import java.util.Optional;

public interface VideoRepository {
    long insert(VideoCreateRequestDto request);
    void updateS3Path(long id, String s3Path);
    Optional<Video> findById(long id);
}
