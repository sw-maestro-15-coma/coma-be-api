package com.swmaestro.cotuber.infra.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.ai.AIProcessor;
import com.swmaestro.cotuber.ai.dto.AIProcessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Primary
@RequiredArgsConstructor
@Component
public class HeatmapAIProcessor implements AIProcessor {
    private static final String AI_URL = "http://43.203.212.4:8080";
    private static final long MOCK_VIDEO_ID = 0L;

    private static final RestClient client = RestClient.create(AI_URL);

    private final ObjectMapper objectMapper;

    @Override
    public AIProcessResponse process(String youtubeUrl) {
        List<Integer> popularPoints = fetchPopularPoints(youtubeUrl);

        if (popularPoints.isEmpty()) {
            throw new IllegalStateException("인기 지점을 찾을 수 없습니다.");
        }

        return new AIProcessResponse(popularPoints.get(0));
    }

    private List<Integer> fetchPopularPoints(String youtubeUrl) {
        try {
            var response = client
                    .post()
                    .uri("/video/" + MOCK_VIDEO_ID + "/popular-point")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(objectMapper.writeValueAsString(new RequestBody(youtubeUrl)))
                    .retrieve()
                    .toEntity(byte[].class);

            validateResponse(response);
            return parsePopularPoints(response);
        } catch (Exception e) {
            throw new IllegalStateException("AI 서버와 통신 중 오류가 발생했습니다.", e);
        }
    }

    private void validateResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("AI 서버와 통신 중 오류가 발생했습니다.");
        }

        if (response.getBody() == null) {
            throw new IllegalStateException("AI 서버와 통신 중 오류가 발생했습니다.");
        }
    }

    private List<Integer> parsePopularPoints(ResponseEntity<?> response) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(response.getBody());
        ResponseBody responseBody = objectMapper.readValue(jsonString, ResponseBody.class);

        return responseBody.popularPoint;
    }

    record RequestBody(String youtubeUrl) {
    }

    record ResponseBody(List<Integer> popularPoint) {
    }
}
