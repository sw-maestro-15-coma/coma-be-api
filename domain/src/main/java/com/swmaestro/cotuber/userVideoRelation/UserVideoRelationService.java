package com.swmaestro.cotuber.userVideoRelation;

import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationListResponseDto;
import com.swmaestro.cotuber.userVideoRelation.dto.UserVideoRelationResponseDto;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserVideoRelationService {
    private final UserVideoRelationRepository userVideoRelationRepository;

    public List<UserVideoRelationListResponseDto> getVideoList(final long userId) {
        final List<UserVideoRelation> shorts = userVideoRelationRepository.findAllByUserId(userId);

        return shorts.stream()
                .map(UserVideoRelationListResponseDto::new)
                .toList();
    }

    public UserVideoRelationResponseDto getVideo(final long userId, final long videoId) {
        final UserVideoRelation shorts = userVideoRelationRepository.findByUserIdAndVideoId(userId, videoId).orElseThrow();
        return new UserVideoRelationResponseDto(shorts);
    }

    public UserVideoRelationResponseDto createRelation(final long userId, final VideoCreateRequestDto createRequestDto) {
        throw new RuntimeException(
            "need to implement this method"
        );
        // NEED impl
    }

    public void generateShorts(final long userId, final long videoId) {
        throw new RuntimeException(
                "need to implement this method"
        );
        // NEED impl
    }
}
