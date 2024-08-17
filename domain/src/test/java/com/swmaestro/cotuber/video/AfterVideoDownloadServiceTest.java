package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.ai.AIProcessProducer;
import com.swmaestro.cotuber.ai.dto.AIProcessMessageRequest;
import com.swmaestro.cotuber.shorts.Shorts;
import com.swmaestro.cotuber.shorts.ShortsRepository;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swmaestro.cotuber.shorts.ProgressState.AI_PROCESSING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AfterVideoDownloadServiceTest {
    final String VIDEO_S3URL = "https//remote.com";
    final String YOUTUBE_URL = "https://youtube.com";
    final String ORIGINAL_TITLE = "original title";
    final String GENERATED_TITLE = "gen title";
    final int FULL_SECOND = 30;

    VideoDownloadMessageResponse response = mockResponse();
    List<AIProcessMessageRequest> mockMessageQueue = new ArrayList<>();

    AIProcessProducer aiProcessProducer = mock(AIProcessProducer.class);
    ShortsRepository shortsRepository = mock(ShortsRepository.class);
    TopTitleGenerator topTitleGenerator = mock(TopTitleGenerator.class);
    VideoRepository videoRepository = mock(VideoRepository.class);

    AfterVideoDownloadService afterService = new AfterVideoDownloadService(
            videoRepository,
            shortsRepository,
            aiProcessProducer,
            topTitleGenerator
    );

    @DisplayName("정상 동작 테스트")
    @Test
    void postProcessTest() {
        // given
        Shorts shorts = Shorts.initialShorts(0L, 0L);
        Video video = Video.initialVideo(new VideoCreateRequestDto(YOUTUBE_URL));

        when(shortsRepository.findById(0L)).thenReturn(Optional.of(shorts));
        when(videoRepository.findById(0L)).thenReturn(Optional.of(video));
        when(topTitleGenerator.makeTopTitle(ORIGINAL_TITLE)).thenReturn(GENERATED_TITLE);
        doAnswer(ans -> {
            mockMessageQueue.add(ans.getArgument(0));
            return null;
        }).when(aiProcessProducer).send(any());

        // when
        afterService.postProcess(response);

        // then
        assertThat(shorts.getProgressState()).isEqualTo(AI_PROCESSING);
        assertThat(shorts.getTopTitle()).isEqualTo(GENERATED_TITLE);
        assertThat(video.getVideoTotalSecond()).isEqualTo(FULL_SECOND);
        assertThat(video.getS3Url()).isEqualTo(VIDEO_S3URL);
        assertThat(mockMessageQueue).hasSize(1);
    }

    @DisplayName("top title generate lambda 실패 시 IllegalStateException 발생")
    @Test
    void throwIllegalStateExceptionIfTitleLambdaFail() {
        // given
        Shorts shorts = Shorts.initialShorts(0L, 0L);
        when(shortsRepository.findById(0L)).thenReturn(Optional.of(shorts));
        when(topTitleGenerator.makeTopTitle(ORIGINAL_TITLE)).thenThrow(RuntimeException.class);

        // when
        ThrowableAssert.ThrowingCallable when = () -> {
            afterService.postProcess(response);
        };

        // then
        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }

    private VideoDownloadMessageResponse mockResponse() {
        return VideoDownloadMessageResponse.builder()
                .videoId(0L)
                .shortsId(0L)
                .originalTitle(ORIGINAL_TITLE)
                .videoFullSecond(FULL_SECOND)
                .s3Url(VIDEO_S3URL)
                .build();
    }
}
