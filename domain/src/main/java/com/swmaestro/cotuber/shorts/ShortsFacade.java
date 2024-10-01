package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageResponse;
import com.swmaestro.cotuber.shorts.dto.ShortsListResponseDto;
import com.swmaestro.cotuber.shorts.dto.ShortsResponseDto;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.DraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ShortsFacade {
    private final ShortsService shortsService;
    private final DraftService draftService;

    public List<ShortsListResponseDto> getShortsList(final long userId) {
        final List<Shorts> shorts = shortsService.getShortsList(userId);

        return shorts.stream()
                .map(ShortsListResponseDto::new)
                .toList();
    }

    public ShortsResponseDto getShorts(final long shortId) {
        final Shorts shorts = shortsService.getShorts(shortId);
        return new ShortsResponseDto(shorts);
    }

    public ShortsResponseDto createShorts(final long userId, final long draftId) {
        Draft relation = draftService.getDraft(draftId);
        Shorts newShorts = shortsService.startShortsGenerate(userId, relation.getVideoId());
        return new ShortsResponseDto(newShorts);
    }

    public void afterShortsGenerate(ShortsGenerateMessageResponse response) {
        shortsService.completeShortsGenerate(response);
    }
}
