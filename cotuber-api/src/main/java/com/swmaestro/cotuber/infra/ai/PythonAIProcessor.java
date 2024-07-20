package com.swmaestro.cotuber.infra.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.ai.AIProcessor;
import com.swmaestro.cotuber.ai.dto.AIProcessResponse;
import com.swmaestro.cotuber.exception.AIProcessFailException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Primary
@Component
@Slf4j
@RequiredArgsConstructor
public class PythonAIProcessor implements AIProcessor {
    private static final String URI = "http://43.203.212.4:8080/video/" + 0 + "/popular-point";

    private final ObjectMapper objectMapper;

    @Override
    public AIProcessResponse process(String youtubeUrl) {
        RestTemplate restTemplate = new RestTemplate();

        RequestBody requestBody = new RequestBody(youtubeUrl);
        String jsonRequestBody;

        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
            log.info("json parsed request body : {}", jsonRequestBody);
        } catch (JsonProcessingException e) {
            throw new AIProcessFailException("request body json 파싱에 실패했습니다");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestBody> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ResponseBody> response = restTemplate.postForEntity(URI, request, ResponseBody.class);

        return AIProcessResponse.builder()
                .popularPointSeconds(extractPopularPoint(response))
                .build();
    }

    private int extractPopularPoint(ResponseEntity<ResponseBody> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new AIProcessFailException("ai 처리 서버 내부 오류가 발생했습니다");
        }

        ResponseBody responseBody = response.getBody();
        if (responseBody == null) {
            throw new AIProcessFailException("response body가 null입니다");
        }

        List<Integer> popularPoints = responseBody.popularPoint;

        if (popularPoints.isEmpty()) {
            throw new AIProcessFailException("가장 많이 다시 본 부분이 없습니다");
        }

        return popularPoints.getFirst();
    }

    @Getter
    static class RequestBody {
        String youtubeUrl;

        RequestBody(String youtubeUrl) {
            this.youtubeUrl = youtubeUrl;
        }

        RequestBody() {
        }
    }

    @Getter
    static class ResponseBody {
        int videoId;
        List<Integer> popularPoint;

        ResponseBody() {
        }

        ResponseBody(int videoId, List<Integer> popularPoint) {
            this.videoId = videoId;
            this.popularPoint = popularPoint;
        }
    }
}

