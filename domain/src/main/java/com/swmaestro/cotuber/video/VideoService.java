package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {
    private final AfterVideoDownloadService afterVideoDownloadService;
    private final VideoRepository videoRepository;
    private final ShortsRepository shortsRepository;
    private final VideoDownloadProducer videoDownloadProducer;

    public VideoCreateResponseDto requestVideoDownload(final long userId, final VideoCreateRequestDto request) {
        Optional<Video> video = videoRepository.findByYoutubeUrl(request.youtubeUrl());

        if (video.isPresent()) {
            return skipDownloadVideo(video.get(), userId);
        } else {
            return downloadVideo(userId, request);
        }
    }

    private VideoCreateResponseDto skipDownloadVideo(Video video, long userId) {
        final Shorts shorts = shortsRepository.save(Shorts.initialShorts(userId, video.getId()));

        afterVideoDownloadService.postProcess(
                VideoDownloadMessageResponse.builder()
                        .videoId(video.getId())
                        .shortsId(shorts.getId())
                        .originalTitle(video.getTitle())
                        .build()
        );

        return VideoCreateResponseDto.builder()
                .id(shorts.getId())
                .build();
    }

    private VideoCreateResponseDto downloadVideo(long userId, VideoCreateRequestDto request) {
        Video newVideo = videoRepository.save(Video.initialVideo(request));
        Shorts shorts = shortsRepository.save(Shorts.initialShorts(userId, newVideo.getId()));

        videoDownloadProducer.send(
                VideoDownloadMessageRequest.builder()
                        .videoId(newVideo.getId())
                        .shortsId(shorts.getId())
                        .build()
        );

        return VideoCreateResponseDto.builder()
                .id(shorts.getId())
                .build();
    }
}
