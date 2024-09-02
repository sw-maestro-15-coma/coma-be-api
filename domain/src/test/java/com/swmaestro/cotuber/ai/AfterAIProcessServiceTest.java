package com.swmaestro.cotuber.ai;

import com.swmaestro.cotuber.ai.dto.AIProcessMessageResponse;
import com.swmaestro.cotuber.shorts.ProgressState;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsProcessProducer;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageRequest;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPoint;
import com.swmaestro.cotuber.shorts.edit.ShortsEditPointRepository;
import com.swmaestro.cotuber.video.Video;
import com.swmaestro.cotuber.video.VideoRepository;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class AfterAIProcessServiceTest {
    ShortsRepository shortsRepository = mock(ShortsRepository.class);
    VideoRepository videoRepository = mock(VideoRepository.class);
    ShortsEditPointRepository shortsEditPointRepository = mock(ShortsEditPointRepository.class);
    ShortsProcessProducer shortsProcessProducer = new MockShortsProcessProducer();
    List<ShortsProcessMessageRequest> requests = new ArrayList<>();

    AfterAIProcessService service = new AfterAIProcessService(
            shortsRepository,
            videoRepository,
            shortsEditPointRepository,
            shortsProcessProducer
    );

    @BeforeEach
    void before() {
        requests.clear();
    }

    @DisplayName("정상 동작 테스트")
    @Test
    void 정상_동작_테스트() {
        // given
        String youtubeUrl = "https://www.youtube.com/watch?v=fWNaR-rxAic";
        Shorts shorts = Shorts.initialShorts(0L, 0L);
        Video video = Video.initialVideo(new VideoCreateRequestDto(youtubeUrl));
        when(shortsRepository.findById(anyLong())).thenReturn(Optional.of(shorts));
        when(videoRepository.findById(anyLong())).thenReturn(Optional.of(video));
        when(shortsEditPointRepository.save(any())).thenReturn(ShortsEditPoint.of(
                0L,
                0L,
                video.getVideoTotalSecond(),
                30));

        // when
        service.postProcess(AIProcessMessageResponse.builder()
                        .videoId(0L)
                        .shortsId(0L)
                        .popularPointSecond(30)
                .build());

        // then
        assertThat(requests).hasSize(1);
        assertThat(shorts.getProgressState()).isEqualTo(ProgressState.SHORTS_GENERATING);
    }

    class MockShortsProcessProducer implements ShortsProcessProducer {
        @Override
        public void send(ShortsProcessMessageRequest request) {
            requests.add(request);
        }
    }
}
