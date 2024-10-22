package com.swmaestro.cotuber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StringUtilTest {

    @Nested
    @DisplayName("secondToFormat는")
    class SecondToFormatTest {
        @DisplayName("초를 시간:분:초 형태로 변환한다.")
        @ParameterizedTest
        @CsvSource({
                "0, 00:00:00",
                "1, 00:00:01",
                "60, 00:01:00",
                "3661, 01:01:01"
        })
        void secondToFormat(int totalSecond, String expected) {
            // when
            String result = StringUtil.secondToFormat(totalSecond);

            // then
            assertThat(result).isEqualTo(expected);
        }

        @DisplayName("음수가 들어오면 예외를 던진다.")
        @Test
        void secondToFormatWithNegative() {
            // given
            int totalSecond = -1;

            // when, then
            assertThatThrownBy(() -> StringUtil.secondToFormat(totalSecond))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("addZeroIfOneDigit는")
    @Nested
    class AddZeroTest {
        @DisplayName("한 자리 수일 때 앞에 0을 추가한다.")
        @Test
        void addZeroIfOneDigit() {
            // given
            int num = 5;

            // when
            String result = StringUtil.addZeroIfOneDigit(num);

            // then
            assertThat(result).isEqualTo("05");
        }

        @DisplayName("두 자리 수일 때 그대로 반환한다.")
        @Test
        void addZeroIfOneDigit2() {
            // given
            int num = 15;

            // when
            String result = StringUtil.addZeroIfOneDigit(num);

            // then
            assertThat(result).isEqualTo("15");
        }
    }
}