package com.swmaestro.cotuber.infra.shorts;

import com.swmaestro.cotuber.domain.batch.dto.ShortsProcessTask;
import com.swmaestro.cotuber.domain.shorts.ShortsProcessor;
import com.swmaestro.cotuber.exception.ShortsProcessFailException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ShortsProcessorImpl implements ShortsProcessor {
    private static final String URI = "http://video.cotuber.com/video/v1/shorts";

    @Override
    public String execute(final ShortsProcessTask task) {
        final RestClient restClient = RestClient.create();

        final ResponseEntity<ResponseBody> response = restClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(task)
                .retrieve()
                .toEntity(ResponseBody.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ShortsProcessFailException("shorts 처리 중 오류가 발생했습니다");
        }

        final ResponseBody body = response.getBody();

        assert body != null;
        return body.shortsUrl;
    }

    static class ResponseBody {
        long id;
        String shortsUrl;

        ResponseBody() {}

        ResponseBody(long id, String shortsUrl) {
            this.id = id;
            this.shortsUrl = shortsUrl;
        }
    }
}
