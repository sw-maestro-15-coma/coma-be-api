package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AfterShortsProcessService {
    private final ShortsRepository shortsRepository;
    private final ShortsThumbnailMaker shortsThumbnailMaker;

    public void postProcessing(ShortsProcessMessageResponse response) {
        Shorts shorts = shortsRepository.findById(response.shortsId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));

        String thumbnailUrl = generateThumbnail(response.shortsId());
        shorts.assignThumbnailUrl(thumbnailUrl);
        shorts.completeShorts(response.link());

        shortsRepository.save(shorts);
    }

    private String generateThumbnail(long shortsId) {
        try {
            return shortsThumbnailMaker.makeThumbnail(shortsId);
        } catch (Exception e) {
            throw new IllegalStateException("shorts 썸네일 생성에 실패했습니다");
        }
    }
}
