package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AfterShortsProcessService {
    private final ShortsRepository shortsRepository;

    public void postProcessing(ShortsProcessMessageResponse response) {
        Shorts shorts = shortsRepository.findById(response.shortsId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
        shorts.completeShorts(response.s3Url(), response.thumbnailUrl());

        shortsRepository.save(shorts);
    }
}
