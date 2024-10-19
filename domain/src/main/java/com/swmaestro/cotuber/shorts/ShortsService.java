package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.draft.DraftService;
import com.swmaestro.cotuber.draft.domain.Draft;
import com.swmaestro.cotuber.draft.dto.SubtitleDto;
import com.swmaestro.cotuber.edit.EditService;
import com.swmaestro.cotuber.edit.domain.Edit;
import com.swmaestro.cotuber.edit.domain.EditSubtitle;
import com.swmaestro.cotuber.shorts.domain.Shorts;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageRequest;
import com.swmaestro.cotuber.shorts.dto.ShortsGenerateMessageResponse;
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.video.domain.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsRepository shortsRepository;
    private final ShortsGenerateProducer shortsProcessProducer;
    private final EditService editService;
    private final DraftService draftService;
    private final VideoService videoService;

    public List<Shorts> getShortsList(final long userId) {
        return shortsRepository.findAllByUserId(userId);
    }

    public Shorts getShorts(final long shortId) {
        return shortsRepository.findById(shortId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 shorts가 없습니다"));
    }

    public Shorts startShortsGenerate(final long userId, final long videoId, final long draftId) {
        Shorts newShorts = shortsRepository.save(
                Shorts.initialShorts(userId, videoId)
        );

        Draft draft = draftService.getDraft(draftId);

        long editId = draft.getEditId();
        List<EditSubtitle> subtitles = editService.getEditSubtitleList(editId);
        List<SubtitleDto> subtitleList = subtitles.stream()
                        .map(s -> SubtitleDto.builder()
                                .start(s.getStart())
                                .end(s.getEnd())
                                .subtitle(s.getSubtitle())
                                .build())
                                .toList();

        Edit edit = editService.getEdit(editId);
        Video video = videoService.getVideo(videoId);

        shortsProcessProducer.send(
                ShortsGenerateMessageRequest.builder()
                        .shortsId(newShorts.getId())
                        .topTitle(edit.getTitle())
                        .videoS3Url(video.getS3Url())
                        .startTime(edit.getStart())
                        .endTime(edit.getEnd())
                        .subtitleList(subtitleList)
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
