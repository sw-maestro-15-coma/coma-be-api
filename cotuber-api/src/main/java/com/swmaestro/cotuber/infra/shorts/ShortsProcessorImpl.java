package com.swmaestro.cotuber.infra.shorts;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swmaestro.cotuber.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.exception.ShortsProcessFailException;
import com.swmaestro.cotuber.shorts.ShortsProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import static com.swmaestro.cotuber.StringUtil.secondToFormat;

@RequiredArgsConstructor
@Component
public class ShortsProcessorImpl implements ShortsProcessor {
    private static final String URI = "http://video.cotuber.com/video/v1/shorts";

    private final ObjectMapper objectMapper;

    @Override
    public String execute(final ShortsProcessTask task) {
        final RestClient restClient = RestClient.create();
        final RequestBody requestBody = new RequestBody(task);
        String jsonBody;

        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new ShortsProcessFailException("request body json 파싱에 실패했습니다");
        }

        final ResponseEntity<ResponseBody> response = restClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(jsonBody)
                .retrieve()
                .toEntity(ResponseBody.class);


        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ShortsProcessFailException("영상 처리 서버에 문제가 발생했습니다");
        }

        final ResponseBody responseBody = response.getBody();

        if (responseBody == null) {
            throw new ShortsProcessFailException("response body가 null 입니다");
        }

        return responseBody.shortsUrl;
    }

    record RequestBody(
            String s3Url,
            String title,
            String start,
            String end
    ) {

        RequestBody(ShortsProcessTask task) {
            this(
                    task.s3Url(),
                    task.topTitle(),
                    secondToFormat(task.start()),
                    secondToFormat(task.end())
            );
        }
    }

    record ResponseBody(
            long id,
            String shortsUrl
    ) {
    }
}
