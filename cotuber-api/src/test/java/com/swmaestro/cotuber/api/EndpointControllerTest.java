package com.swmaestro.cotuber.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.user.UserReader;
import com.swmaestro.cotuber.validate.Validator;
import com.swmaestro.cotuber.validate.YoutubeUrlPattern;
import com.swmaestro.cotuber.video.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "0")
@WebMvcTest(EndpointController.class)
@Import({Validator.class, YoutubeUrlPattern.class})
class EndpointControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    VideoService videoService;
    @MockBean
    ShortsService shortsService;
    @MockBean
    UserReader userReader;

    final String invalidYoutubeUrl = "https://abc.com";
    final String validYoutubeUrl = "https://www.youtube.com/watch?v=abc12345678";

    @DisplayName("get video-list")
    @Test
    void testGetVideoList() throws Exception {
        mockMvc.perform(
                get("/api/v1/video-list")
        )
                .andExpect(status().isOk());
    }

    @DisplayName("get video-list with data")
    @Test
    void testGetVideoListWithData() throws Exception {
        // given
        // 이렇게 하는게 아닌듯? 서비스 메소드 호출한다고 리턴 데이터가 바뀌지 않는것 같다..
        /*
        videoService.requestVideoDownload(validYoutubeUrl);
        UserVideoRelationResponseDto responseDto = UserVideoRelationResponseDto.builder()
                .id(0L)
                .status(UserVideoRelationStatus.VIDEO_DOWNLOADING)
                .build();
        String jsonMappedResponse = objectMapper.writeValueAsString(List.of(responseDto));

        mockMvc.perform(
                get("/api/v1/video-list")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonMappedResponse));

         */
    }
    /*

    @DisplayName("post video with invalid youtube url")
    @Test
    void testPostVideoWithInvalidYoutubeUrl() throws Exception {
        // given
        UserVideoRelationCreateRequestDto requestDto = new UserVideoRelationCreateRequestDto(invalidYoutubeUrl);
        String jsonMapped = objectMapper.writeValueAsString(requestDto);

        // when
        ThrowableAssert.ThrowingCallable when = () -> mockMvc.perform(
                post("/api/v1/video")
                        .content(jsonMapped)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        // then
        assertThatThrownBy(when).isInstanceOf(ServletException.class);
    }

    @DisplayName("post video with valid youtube url")
    @Test
    void testPostVideoWithValidYoutubeUrl() throws Exception {
        // given
        UserVideoRelationCreateRequestDto requestDto = new UserVideoRelationCreateRequestDto(validYoutubeUrl);
        UserVideoRelationResponseDto responseDto = UserVideoRelationResponseDto.builder()
                .id(0L)
                .status(UserVideoRelationStatus.VIDEO_DOWNLOADING)
                .build();
        String jsonMapped = objectMapper.writeValueAsString(requestDto);
        String jsonMappedResponse = objectMapper.writeValueAsString(responseDto);

        // when
        mockMvc.perform(
                post("/api/v1/video")
                        .content(jsonMapped)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(jsonMappedResponse)); // 여기도 뭔가 이상함.. 왜 리턴이 <> 로 나오는거지? postman으로 했을때는 잘 되는데...
    }

     */
}
