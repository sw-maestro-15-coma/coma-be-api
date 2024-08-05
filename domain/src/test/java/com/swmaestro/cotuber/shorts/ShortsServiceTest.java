package com.swmaestro.cotuber.shorts;

import com.swmaestro.cotuber.exception.ShortsMakingFailException;
import com.swmaestro.cotuber.log.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class ShortsServiceTest {
    ShortsRepository shortsRepository = new ShortsMockRepository();
    LogService logService = mock(LogService.class);

    Shorts mockShorts = Shorts.builder()
            .id(1)
            .userId(1)
            .videoId(1)
            .build();

    @BeforeEach
    void setUp() {
        ((ShortsMockRepository) shortsRepository).clear();
        shortsRepository.save(mockShorts);
    }

    @DisplayName("shorts를 만드는 데에 성공하면 shorts에 link, thumbnail이 저장되고 상태가 완료로 변경된다.")
    @Test
    void makeShorts() {
        // given
        var task = ShortsProcessTask.builder()
                .userId(1)
                .s3Url("https://s3.url.com")
                .topTitle("치킨을 먹는 방법")
                .start(61)
                .end(70)
                .shortsId(1)
                .editPointId(1)
                .build();

        ShortsService shortsService = new ShortsService(
                task1 -> "https://생성된쇼츠의s3url.com",
                shortsRepository,
                logService,
                task1 -> "https://생성된썸네일의s3url.com"
        );

        // when
        shortsService.makeShorts(task);

        // then
        Shorts shorts = shortsRepository.findById(mockShorts.getId())
                .orElseThrow();

        assertThat(shorts.getLink()).isEqualTo("https://생성된쇼츠의s3url.com");
        assertThat(shorts.getThumbnailUrl()).isEqualTo("https://생성된썸네일의s3url.com");
        assertThat(shorts.getProgressState()).isEqualTo(ProgressState.COMPLETE);
    }

    @DisplayName("shorts 생성 요청 중 오류가 발생하면 shorts의 상태가 에러로 변경된다.")
    @Test
    void makeShortsError() {
        // given
        var task = ShortsProcessTask.builder()
                .userId(1)
                .s3Url("https://s3.url.com")
                .topTitle("치킨을 먹는 방법")
                .start(61)
                .end(70)
                .shortsId(1)
                .editPointId(1)
                .build();

        ShortsService shortsService = new ShortsService(
                task1 -> {
                    throw new RuntimeException("shorts 생성 중 오류 발생");
                },
                shortsRepository,
                logService,
                task1 -> "https://생성된썸네일의s3url.com"
        );

        // when
        assertThatThrownBy(() -> shortsService.makeShorts(task))
                .isInstanceOf(ShortsMakingFailException.class);

        // then
        Shorts shorts = shortsRepository.findById(1).orElseThrow();
        assertThat(shorts.getProgressState()).isEqualTo(ProgressState.ERROR);
    }

    @DisplayName("shorts 썸네일 생성 요청 중 오류가 발생하면 shorts의 상태가 에러로 변경된다.")
    @Test
    void makeShortsThumbnailError() {
        // given
        var task = ShortsProcessTask.builder()
                .userId(1)
                .s3Url("https://s3.url.com")
                .topTitle("치킨을 먹는 방법")
                .start(61)
                .end(70)
                .shortsId(1)
                .editPointId(1)
                .build();

        ShortsService shortsService = new ShortsService(
                task1 -> "https://생성된쇼츠의s3url.com",
                shortsRepository,
                logService,
                task1 -> {
                    throw new RuntimeException("썸네일 생성 중 오류 발생");
                }
        );

        // when
        assertThatThrownBy(() -> shortsService.makeShorts(task))
                .isInstanceOf(ShortsMakingFailException.class);

        // then
        Shorts shorts = shortsRepository.findById(1).orElseThrow();
        assertThat(shorts.getProgressState()).isEqualTo(ProgressState.ERROR);
        assertThat(shorts.getThumbnailUrl()).isNull();
    }

}