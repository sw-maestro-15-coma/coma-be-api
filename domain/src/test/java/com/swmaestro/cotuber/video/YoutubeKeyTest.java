package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.domain.YoutubeKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("YoutubeKey는")
class YoutubeKeyTest {
    @DisplayName("정상적인 youtube url을 넣으면 문제 없이 생성된다")
    @ParameterizedTest
    @ValueSource(strings = {
            "https://www.youtube.com/watch?v=fWNaR-rxAic",
            "https://m.youtube.com/watch?v=fWNaR-rxAic",
            "https://www.youtube.com/watch?v=fWNaR-rxAic&feature=youtu.be",
    })
    void create_with_valid_youtube_url(String url) {
        // when
        YoutubeKey youtubeKey = new YoutubeKey(url);

        // then
        assertThat(youtubeKey).isNotNull();
    }

    @DisplayName("정상적인 youtube 공유 url을 넣으면 문제 없이 생성된다")
    @Test
    void create_with_valid_youtube_share_url() {
        // given
        String url = "https://youtu.be/fWNaR-rxAic?si=qi1YsYPKwLpNHElR";

        // when
        YoutubeKey youtubeKey = new YoutubeKey(url);

        // then
        assertThat(youtubeKey).isNotNull();
    }

    @DisplayName("정상적인 youtube embed url을 넣으면 문제 없이 생성된다")
    @Test
    void create_with_valid_youtube_embed_url() {        // given
        String url = "https://www.youtube.com/embed/BqgCJzSOT2k?si=h6qJYrUTgGFlCv6U";

        // when
        YoutubeKey youtubeKey = new YoutubeKey(url);

        // then
        assertThat(youtubeKey).isNotNull();
    }

    @DisplayName("정상적인 youtube 영상 url을 넣으면 key를 받아올 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {
            "https://www.youtube.com/watch?v=fWNaR-rxAic",
            "https://m.youtube.com/watch?v=fWNaR-rxAic"
    })
    void can_get_key_with_valid_youtube_url(String url) {
        // when
        YoutubeKey youtubeKey = new YoutubeKey(url);
        String key = youtubeKey.getKey();

        // then
        assertThat(key).isEqualTo("fWNaR-rxAic");
    }

    @DisplayName("정상적인 youtube 공유 url을 넣으면 key를 받아올 수 있다")
    @Test
    void can_get_key_with_valid_youtube_share_url() {
        // given
        String url = "https://youtu.be/fWNaR-rxAic?si=qi1YsYPKwLpNHElR";

        // when
        YoutubeKey youtubeKey = new YoutubeKey(url);
        String key = youtubeKey.getKey();

        // then
        assertThat(key).isEqualTo("fWNaR-rxAic");
    }

    @DisplayName("정상적인 youtube embed url을 넣으면 key를 받아올 수 있다")
    @Test
    void can_get_key_with_valid_youtube_embed_url() {
        // given
        String url = "https://www.youtube.com/embed/fWNaR-rxAic";

        // when
        YoutubeKey youtubeKey = new YoutubeKey(url);
        String key = youtubeKey.getKey();

        // then
        assertThat(key).isEqualTo("fWNaR-rxAic");
    }

    @DisplayName("youtube가 아닌 url을 넣으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "https://google.com",
            "https://naver.com",
            "https://youtube.co.kr",
            "asdf",
    })
    void throw_exception_with_non_youtube_url(String url) {
        // when & then
        assertThatThrownBy(() -> new YoutubeKey(url))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("null을 넣으면 예외가 발생한다")
    @Test
    void throw_exception_with_null() {
        // when & then
        assertThatThrownBy(() -> new YoutubeKey(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("youtube url이지만 key가 없는 url을 넣으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "https://youtube.com/watch",
            "https://youtube.com",
            "https://youtube.com/watch?v=",
            "https://youtube.com/watch?a=123&b=as",
    })
    void throw_exception_with_no_key_youtube_url(String url) {
        // when & then
        assertThatThrownBy(() -> new YoutubeKey(url))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("youtube 공유 url이지만 key가 없는 url을 넣으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "https://youtu.be"
    })
    void throw_exception_with_no_key_youtube_share_url(String url) {
        // when & then
        assertThatThrownBy(() -> new YoutubeKey(url))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("youtube 공유 url이지만 key가 없는 url을 넣으면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "https://www.youtube.com/embed",
            "https://www.youtube.com/embed/",
    })
    void throw_exception_with_no_key_youtube_embed_url(String url) {
        // when & then
        assertThatThrownBy(() -> new YoutubeKey(url))
                .isInstanceOf(IllegalArgumentException.class);
    }
}