package com.swmaestro.cotuber.infra.video;

import com.swmaestro.cotuber.exception.VideoDownloadFailException;
import com.swmaestro.cotuber.video.YoutubeVideoDownloader;
import com.swmaestro.cotuber.video.dto.VideoDownloadResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
public class YoutubeVideoDownloaderImpl implements YoutubeVideoDownloader {
    private static final String URI = "http://video.cotuber.com/video/v1/download-youtube";
    private static final String ERROR_MESSAGE = "youtube 다운로드 중 오류가 발생했습니다";

    @Override
    public VideoDownloadResponse download(String youtubeUrl) {
        final RestClient restClient = RestClient.create();

        final ResponseEntity<ResponseBody> response = restClient.post()
                .uri(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(new RequestBody(youtubeUrl))
                .retrieve()
                .toEntity(ResponseBody.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new VideoDownloadFailException(ERROR_MESSAGE);
        }

        final ResponseBody body = response.getBody();

        if (body == null) {
            throw new VideoDownloadFailException(ERROR_MESSAGE);
        }

        return VideoDownloadResponse.builder()
                .s3Url(body.s3Url)
                .length(body.length)
                .originalTitle(body.originalTitle)
                .build();
    }

    static class RequestBody {
        String url;

        RequestBody(String url) {
            this.url = url;
        }
    }

    static class ResponseBody {
        String s3Url;
        int length;
        String originalTitle;

        ResponseBody() {
        }

        ResponseBody(String s3Url, int length, String originalTitle) {
            this.s3Url = s3Url;
            this.length = length;
            this.originalTitle = originalTitle;
        }
    }
}
