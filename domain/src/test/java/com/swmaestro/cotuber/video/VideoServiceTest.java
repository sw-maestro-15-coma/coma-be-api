package com.swmaestro.cotuber.video;

import com.swmaestro.cotuber.video.dto.VideoDownloadMessageRequest;
import com.swmaestro.cotuber.video.dto.VideoDownloadMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VideoServiceTest {
    /* need impl
    VideoMockRepository videoMockRepository = new VideoMockRepository();
    ShortsMockRepository shortsMockRepository = new ShortsMockRepository();
    AfterVideoDownloadService afterVideoDownloadService = mock(AfterVideoDownloadService.class);
    VideoDownloadProducer videoDownloadProducer = mock(VideoDownloadProducer.class);

    VideoService videoService;

    String youtubeUrl = "https://www.youtube.com/watch?v=1234";

    @BeforeEach
    void setUp() {
        videoMockRepository.clear();
        shortsMockRepository.clear();

        videoService = new VideoService(
                afterVideoDownloadService,
                videoMockRepository,
                shortsMockRepository,
                videoDownloadProducer
        );
    }

    @DisplayName("쇼츠 생성을 요청할 경우")
    @Nested
    class RequestCreateShorts {
        @DisplayName("쇼츠가 생성되어야 한다")
        @Test
        void requestCreateShorts() {
            // when
            videoService.requestVideoDownload(1L, new UserVideoRelationCreateRequestDto(youtubeUrl));

            // then
            assertThat(shortsMockRepository.findAll()).hasSize(1);
        }

        @DisplayName("생성될 쇼츠의 id를 반환해야 한다")
        @Test
        void requestVideoDownload() {
            // when
            var response = videoService.requestVideoDownload(1L, new UserVideoRelationCreateRequestDto(youtubeUrl));

            // then
            Optional<Shorts> createdShorts = shortsMockRepository.findById(response.id());

            assertThat(createdShorts).isPresent();
            assertThat(createdShorts.get().getId()).isEqualTo(response.id());
        }

        @DisplayName("비디오가 이미 존재하는 경우")
        @Nested
        class WhenAlreadyVideoExists {
            @DisplayName("비디오 엔티티 생성을 건너뛰어야 한다")
            @Test
            void skipDownloadVideoWhenAlreadyVideoExists() {
                // given
                long STUB_VIDEO_ID = 0;
                Video stubVideo = Video.builder()
                        .id(STUB_VIDEO_ID)
                        .s3Url(null)
                        .title("테스트 제목")
                        .youtubeUrl(youtubeUrl)
                        .build();

                videoMockRepository.save(stubVideo);

                // when
                videoService.requestVideoDownload(1L, new UserVideoRelationCreateRequestDto(youtubeUrl));

                // then
                assertThat(videoMockRepository.findAll()).hasSize(1);
                assertThat(videoMockRepository.findAll()).contains(stubVideo);
            }

            @DisplayName("바로 비디오 다운 이후 동작을 요청해야 한다")
            @Test
            void postProcessAfterVideoDownload() {
                // given
                AfterVideoDownloadService mockedAfterVideoDownloadService = mock(AfterVideoDownloadService.class);
                videoService = new VideoService(
                        mockedAfterVideoDownloadService,
                        videoMockRepository,
                        shortsMockRepository,
                        mock(VideoDownloadProducer.class)
                );

                Video stubVideo = Video.builder()
                        .id(0)
                        .s3Url(null)
                        .title("테스트 제목")
                        .youtubeUrl(youtubeUrl)
                        .build();

                videoMockRepository.save(stubVideo);

                // when
                videoService.requestVideoDownload(1L, new UserVideoRelationCreateRequestDto(youtubeUrl));

                // then
                assertThat(shortsMockRepository.findAll()).hasSize(1);
                var createdShorts = shortsMockRepository.findAll().get(0);

                verify(mockedAfterVideoDownloadService).postProcess(
                        VideoDownloadMessageResponse.builder()
                                .videoId(0)
                                .shortsId(createdShorts.getId())
                                .originalTitle(stubVideo.getTitle())
                                .build()
                );
            }
        }

        @DisplayName("비디오가 존재하지 않는 경우")
        @Nested
        class WhenVideoNotExists {
            @DisplayName("비디오 엔티티를 생성해야 한다")
            @Test
            void requestVideoDownloadWhenVideoNotExists() {
                // given
                videoService = new VideoService(
                        mock(AfterVideoDownloadService.class),
                        videoMockRepository,
                        shortsMockRepository,
                        mock(VideoDownloadProducer.class)
                );

                // when
                videoService.requestVideoDownload(1L, new UserVideoRelationCreateRequestDto(youtubeUrl));

                // then
                assertThat(videoMockRepository.findAll()).hasSize(1);
                assertThat(videoMockRepository.findByYoutubeUrl(youtubeUrl)).isPresent();
            }

            @DisplayName("비디오 다운로드 메시지를 전송해야 한다")
            @Test
            void sendVideoDownloadMessage() {
                // given
                VideoDownloadProducer mockedVideoDownloadProducer = mock(VideoDownloadProducer.class);
                videoService = new VideoService(
                        afterVideoDownloadService,
                        videoMockRepository,
                        shortsMockRepository,
                        mockedVideoDownloadProducer
                );

                // when
                videoService.requestVideoDownload(1L, new UserVideoRelationCreateRequestDto(youtubeUrl));

                // then
                verify(mockedVideoDownloadProducer).send(
                        VideoDownloadMessageRequest.builder()
                                .videoId(0)
                                .shortsId(0)
                                .build()
                );
            }
        }
    }
     */
}
