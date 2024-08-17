package com.swmaestro.cotuber.api;

import com.swmaestro.cotuber.dashboard.DashboardService;
import com.swmaestro.cotuber.dashboard.dto.DashboardListResponseDto;
import com.swmaestro.cotuber.shorts.ShortsService;
import com.swmaestro.cotuber.user.UserReader;
import com.swmaestro.cotuber.validate.Validator;
import com.swmaestro.cotuber.video.VideoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EndpointController.class)
class EndpointControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    VideoService videoService;
    @MockBean
    ShortsService shortsService;
    @MockBean
    DashboardService dashboardService;
    @MockBean
    UserReader userReader;
    @MockBean
    Validator validator;

    @DisplayName("")
    @Test
    @WithMockUser(username = "0")
    void test() throws Exception {
        when(dashboardService.getDashboard(0L)).thenReturn(List.of(
                        DashboardListResponseDto.builder().build()
                )
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
