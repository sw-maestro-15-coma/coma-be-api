package com.swmaestro.cotuber.userVideoRelation;

import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationListResponseDto;
import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationResponseDto;
import com.swmaestro.cotuber.video.AfterVideoDownloadService;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationCreateRequestDto;
import com.swmaestro.cotuber.video.VideoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserVideoRelationService {
    private final VideoService videoService;
    private final UserVideoRelationRepository userVideoRelationRepository;
    private final AfterVideoDownloadService afterVideoDownloadService;

    public List<UserVideoRelationListResponseDto> getVideoList(final long userId) {
        final List<UserVideoRelation> relationList = userVideoRelationRepository.findAllByUserId(userId);

        return relationList.stream()
                .map(UserVideoRelationListResponseDto::new)
                .toList();
    }

    public UserVideoRelationResponseDto getVideo(final long userVideoRelationId) {
        final UserVideoRelation relation = userVideoRelationRepository.findById(userVideoRelationId).orElseThrow();
        return new UserVideoRelationResponseDto(relation);
    }

    public UserVideoRelationResponseDto createRelation(final long userId, final UserVideoRelationCreateRequestDto createRequestDto) {
        try {
            Video video = videoService.getVideoByYoutubeUrl(createRequestDto.youtubeUrl());
            if (video.getVideoStatus() != VideoStatus.COMPLETE) throw new RuntimeException();
            UserVideoRelation newRelation = userVideoRelationRepository.save(
                UserVideoRelation.builder()
                        .userId(userId)
                        .videoId(video.getId())
                        .userVideoRelationStatus(UserVideoRelationStatus.AI_PROCESSING)
                        .build()
            );
            afterVideoDownloadService.publishToAIProducer(newRelation.getId(), video.getS3Url());
            // need to put video Response Dto to userVideoRelationResponseDto
            return new UserVideoRelationResponseDto(newRelation);
        } catch (Exception e) {
            Video video = videoService.requestVideoDownload(createRequestDto.youtubeUrl());
            UserVideoRelation newRelation = userVideoRelationRepository.save(
                    UserVideoRelation.builder()
                            .userId(userId)
                            .videoId(video.getId())
                            .userVideoRelationStatus(UserVideoRelationStatus.VIDEO_DOWNLOADING)
                            .build()
            );
            // need to put video Response Dto to userVideoRelationResponseDto
            return new UserVideoRelationResponseDto(newRelation);
        }

    }
}
