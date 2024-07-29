package com.swmaestro.cotuber.infra.shorts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.shorts.ShortsThumbnailMaker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Component
public class LambdaShortsThumbnailMaker implements ShortsThumbnailMaker {
    private static final String URI = "https://api.cotuber.com/lambda/create-thumbnail";

    private final ObjectMapper objectMapper;

    @Override
    public String makeThumbnail(long shortsId) {
        RestClient restClient = RestClient.create();
        RequestBody requestBody = new RequestBody(shortsId);
        String jsonBody;

        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("shorts thumbnail request body json 파싱에 실패했습니다 : " + e.getMessage());
        }

        final ResponseEntity<byte[]> response = restClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBody)
                .retrieve()
                .toEntity(byte[].class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("thumbnail 람다 내부 문제가 발생했습니다");
        }

        final byte[] body = response.getBody();

        if (body == null) {
            throw new IllegalStateException("thumbnail 람다 response body가 null입니다");
        }

        String decoded = new String(response.getBody(), StandardCharsets.UTF_8);

        try {
            ResponseBody responseBody = objectMapper.readValue(decoded, ResponseBody.class);

            return responseBody.thumbnailUrl;
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("thumbnail 람다 response body json 파싱에 실패했습니다");
        }
    }

    record RequestBody(Long shortsId) {
    }

    record ResponseBody(String message, String thumbnailUrl) {
    }
}

