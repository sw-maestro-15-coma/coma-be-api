package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.shorts.dto.ShortsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsRepository shortsRepository;

    public List<ShortsListResponseDto> getShortsList(final long userId) {
        final List<Shorts> shorts = shortsRepository.findAllByUserId(userId);

        return shorts.stream()
                .map(ShortsListResponseDto::new)
                .toList();
    }

    public ShortsResponseDto getShorts(final long shortId) {
        final Shorts shorts = shortsRepository.findById(shortId).orElseThrow();
        return new ShortsResponseDto(shorts);
    }
}
