package com.swmaestro.cotuber.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.dashboard.DashboardService;
import com.swmaestro.cotuber.dashboard.dto.DashboardListResponseDto;
import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.user.UserReader;
import com.swmaestro.cotuber.validate.Validator;
import com.swmaestro.cotuber.validate.YoutubeUrlPattern;
import com.swmaestro.cotuber.video.VideoService;
import com.swmaestro.cotuber.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.video.dto.VideoCreateResponseDto;
import jakarta.servlet.ServletException;
import org.assertj.core.api.ThrowableAssert;
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
import static org.mockito.Mockito.*;
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
    DashboardService dashboardService;
    @MockBean
    UserReader userReader;

    @DisplayName("")
    @Test
    void test() throws Exception {
        when(dashboardService.getDashboard(0L)).thenReturn(List.of(
                        DashboardListResponseDto.builder().build()
                )
        );

        mockMvc.perform(get("/api/v1/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }


    @DisplayName("updateVideo - 유효하지 않은 youtube url")
    @Test
    void test1() throws Exception {
        // given
        final String invalidYoutubeUrl = "https://abc.com";
        VideoCreateRequestDto requestDto = new VideoCreateRequestDto(invalidYoutubeUrl);
        String jsonMapped = objectMapper.writeValueAsString(requestDto);

        ThrowableAssert.ThrowingCallable when = () -> mockMvc.perform(
                post("/api/v1/video")
                        .content(jsonMapped)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        );

        assertThatThrownBy(when).isInstanceOf(ServletException.class);
    }

    @DisplayName("")
    @Test
    void test2() throws Exception {
        // given
        final String validYoutubeUrl = "https://www.youtube.com/watch?v=abc12345678";
        VideoCreateRequestDto requestDto = new VideoCreateRequestDto(validYoutubeUrl);
        VideoCreateResponseDto responseDto = VideoCreateResponseDto.builder()
                .id(0L)
                .build();
        String jsonMapped = objectMapper.writeValueAsString(requestDto);
        String jsonMappedResponse = objectMapper.writeValueAsString(responseDto);

        when(videoService.requestVideoDownload(0L, requestDto)).thenReturn(responseDto);

        mockMvc.perform(
                post("/api/v1/video")
                        .content(jsonMapped)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(jsonMappedResponse));
    }
}
