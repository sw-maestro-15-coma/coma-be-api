package com.swmaestro.cotuber.infra.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.exception.ShortsTopTitleGenerateFailException;
import com.swmaestro.cotuber.video.TopTitleGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class LambdaTopTitleGenerator implements TopTitleGenerator {
    private static final String URI = "https://api.cotuber.com/lambda/create-shorts-title-gpt";

    private final ObjectMapper objectMapper;

    @Override
    public String makeTopTitle(String originalTitle) {
        final RestClient restClient = RestClient.create();
        final RequestBody requestBody = new RequestBody(originalTitle);
        String jsonBody;

        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new ShortsTopTitleGenerateFailException("json 파싱에 실패했습니다 : " + e.getMessage());
        }

        final ResponseEntity<byte[]> response = restClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBody)
                .retrieve()
                .toEntity(byte[].class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ShortsTopTitleGenerateFailException("람다 내부 문제가 발생했습니다");
        }

        final byte[] body = response.getBody();

        if (body == null) {
            throw new ShortsTopTitleGenerateFailException("response body가 null입니다");
        }

        String decoded = new String(response.getBody(), StandardCharsets.UTF_8);

        try {
            ResponseBody responseBody = objectMapper.readValue(decoded, ResponseBody.class);

            return responseBody.generatedTitle;
        } catch (JsonProcessingException e) {
            throw new ShortsTopTitleGenerateFailException("response body json 파싱에 실패했습니다");
        }
    }

    @Getter
    static class RequestBody {
        String title;

        RequestBody() {
        }

        RequestBody(String title) {
            this.title = title;
        }
    }

    @Getter
    static class ResponseBody {
        @JsonProperty("shorts_title")
        String generatedTitle;

        ResponseBody() {
        }

        ResponseBody(String generatedTitle) {
            this.generatedTitle = generatedTitle;
        }
    }
}
