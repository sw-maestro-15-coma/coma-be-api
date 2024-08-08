package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.util.HashMapRepository;

import java.util.Optional;

class VideoMockRepository extends HashMapRepository<Video> implements VideoRepository {
    @Override
    public Video save(Video video) {
        super.save(video.getId(), video);
        return video;
    }

    @Override
    public Optional<Video> findByYoutubeUrl(String youtubeUrl) {
        return findAll().stream()
                .filter(video -> video.getYoutubeUrl().equals(youtubeUrl))
                .findFirst();
    }
}
