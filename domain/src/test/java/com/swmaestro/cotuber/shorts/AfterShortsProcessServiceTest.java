package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.shorts.dto.ShortsProcessMessageResponse;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.swmaestro.cotuber.shorts.ProgressState.COMPLETE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;

class AfterShortsProcessServiceTest {
    ShortsRepository mockShortsRepository = Mockito.mock(ShortsRepository.class);
    ShortsThumbnailMaker mockShortsThumbnailMaker = Mockito.mock(ShortsThumbnailMaker.class);
    AfterShortsProcessService service = new AfterShortsProcessService(
            mockShortsRepository,
            mockShortsThumbnailMaker
    );

    @DisplayName("썸네일 생성기에서 오류가 발생하면 IllegalStateException으로 치환해야함")
    @Test
    void thumbnail_오류_테스트() {
        // given
        Mockito.when(mockShortsThumbnailMaker.makeThumbnail(anyLong())).thenThrow(RuntimeException.class);
        Mockito.when(mockShortsRepository.findById(anyLong())).thenReturn(Optional.of(Shorts.initialShorts(0L, 0L)));

        // when
        ThrowableAssert.ThrowingCallable when = () -> {
            service.postProcessing(ShortsProcessMessageResponse.builder().build());
        };

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("정상 동작 테스트")
    @Test
    void 정상_동작_테스트() {
        // given
        String thumbnailUrl = "thumbnail_url";
        Shorts shorts = Shorts.initialShorts(0L, 0L);
        Mockito.when(mockShortsRepository.findById(anyLong())).thenReturn(Optional.of(shorts));
        Mockito.when(mockShortsThumbnailMaker.makeThumbnail(anyLong())).thenReturn(thumbnailUrl);

        // when
        service.postProcessing(ShortsProcessMessageResponse.builder()
                .shortsId(0L)
                .videoId(0L)
                .link("link")
                .build());

        // then
        assertThat(shorts.getThumbnailUrl()).isEqualTo(thumbnailUrl);
        assertThat(shorts.getProgressState()).isEqualTo(COMPLETE);
    }
}
