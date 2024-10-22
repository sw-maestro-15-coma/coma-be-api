package com.swmaestro.cotuber.shorts.edit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
/*
class ShortsEditPointTest {
    final long shortsId = 0L;
    final long videoId = 1L;

    @DisplayName("최대 길이, 편집점 모두 30초보다 적을 때는 시작 - 0초, 끝 - 최대 길이")
    @Test
    void countStartAndEndTest() {
        // given
        final int fullLength = 30;
        final int popularSecond = 10;

        ShortsEditPoint shortsEditPoint = ShortsEditPoint.of(
                shortsId,
                videoId,
                fullLength,
                popularSecond
        );

        // when
        int start = shortsEditPoint.getStartSecond();
        int end = shortsEditPoint.getEndSecond();

        // then
        assertThat(start).isZero();
        assertThat(end).isEqualTo(30);
    }

    @DisplayName("최대 길이, 편집점 모두 0일 때에는 start와 end가 0이어야 한다")
    @Test
    void startAndEndAllZero() {
        // given
        final int fullLength = 0;
        final int popularSecond = 0;

        ShortsEditPoint shortsEditPoint = ShortsEditPoint.of(
                shortsId,
                videoId,
                fullLength,
                popularSecond
        );

        // when
        int start = shortsEditPoint.getStartSecond();
        int end = shortsEditPoint.getEndSecond();

        // then
        assertThat(start).isZero();
        assertThat(end).isZero();
    }
}
*/