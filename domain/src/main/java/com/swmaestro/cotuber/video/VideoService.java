package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final ShortsRepository shortsRepository;
    private final VideoDownloadProducer videoDownloadProducer;

    public VideoCreateResponseDto requestVideoDownload(final long userId, final VideoCreateRequestDto request) {
        final Video video = videoRepository.save(Video.initialVideo(request));
        final Shorts shorts = shortsRepository.save(Shorts.initialShorts(userId, video.getId(), "제목 생성중..."));

        videoDownloadProducer.send(
                VideoDownloadMessageRequest.builder()
                        .videoId(video.getId())
                        .shortsId(shorts.getId())
                        .build()
        );

        return VideoCreateResponseDto.builder()
                .id(video.getId())
                .build();
    }
}
