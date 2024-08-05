package com.swmaestro.cotuber.validate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ValidatorTests {
    YoutubeUrlPattern youtubeUrlPattern = new YoutubeUrlPattern();
    Validator validator = new Validator(youtubeUrlPattern);

    @DisplayName("youtube url 유효성 검사 로직 검증 - 성공하는 경우")
    @Test
    void youtubeUrlValidateSuccessTest() {
        // given
        List<String> successUrls = List.of(
                "https://www.youtube.com/watch?v=abc12345678",
                "https://youtu.be/abc12345678",
                "https://youtube.com/v/abc12345678",
                "https://www.youtube.com/embed/abc12345678"
        );
        // when
        successUrls.forEach(url -> {
            validator.checkYoutubeUrl(url);
        });
        // then
    }

    @DisplayName("youtube url 유효성 검사 로직 검증 - 실패하는 경우")
    @Test
    void youtubeUrlValidateFailTest() {
        // given
        List<String> failUrls = List.of(
                "https://www.example.com/watch?v=abc12345678",
                "https://abc.com",
                "",
                "abcdefg"
        );
        // when
        failUrls.forEach(url -> {
            // then
            assertThatThrownBy(() -> {
                validator.checkYoutubeUrl(url);
            }).isInstanceOf(IllegalArgumentException.class);
        });
    }
}
