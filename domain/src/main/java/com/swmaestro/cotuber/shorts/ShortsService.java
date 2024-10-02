package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageRequest;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsRepository shortsRepository;
    private final ShortsGenerateProducer shortsProcessProducer;

    public List<Shorts> getShortsList(final long userId) {
        return shortsRepository.findAllByUserId(userId);
    }

    public Shorts getShorts(final long shortId) {
        return shortsRepository.findById(shortId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
    }

    public Shorts startShortsGenerate(final long userId, final long videoId) {
        Shorts newShorts = shortsRepository.save(
                Shorts.initialShorts(userId, videoId)
        );
        shortsProcessProducer.send(
                ShortsGenerateMessageRequest.builder()
                        .shortsId(newShorts.getId())
                        // need impl
                        .build()
        );
        return newShorts;
    }

    public void completeShortsGenerate(ShortsGenerateMessageResponse response) {
        Shorts shorts = shortsRepository.findById(response.shortsId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
        shorts.completeShorts(response.s3Url(), response.thumbnailUrl());

        shortsRepository.save(shorts);
    }
}
