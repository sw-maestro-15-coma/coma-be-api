package com.swmaestro.cotuber.api.video;

import com.swmaestro.cotuber.common.ResponseMessage;
import com.swmaestro.cotuber.domain.video.dto.VideoCreateRequestDto;
import com.swmaestro.cotuber.domain.video.dto.VideoListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Video", description = "비디오 관련 API")
@RequiredArgsConstructor
@RestController
public class VideoController {
    private static final String SUCCESS = "success";

    @Operation(summary = "대시보드의 동영상 목록 조회")
    @GetMapping("/videos")
    public ResponseEntity<List<VideoListResponseDto>> getVideos() {
        final List<VideoListResponseDto> responses = List.of(
                new VideoListResponseDto("http://youtube.com/test1", "http://youtube.com/test1.png"),
                new VideoListResponseDto("http://youtube.com/test2", "http://youtube.com/test2.png"),
                new VideoListResponseDto("http://youtube.com/test3", "http://youtube.com/test3.png")
        );
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "유튜브 링크로 동영상 업로드")
    @PostMapping("/videos")
    public ResponseEntity<ResponseMessage> uploadVideo(@RequestBody VideoCreateRequestDto request) {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }

    /*
    2024-07-01: 논의 필요
     */
    @Operation(summary = "동영상 편집 상황 조회")
    @GetMapping("/videos/{videoId}")
    public void getVideo() {
    }

    /*
    2024-07-01: 논의 필요
     */
    @Operation(summary = "동영상 편집 상황 수정")
    @PostMapping("/videos/{videoId}")
    public void updateVideo() {
    }

    @Operation(summary = "동영상 삭제")
    @DeleteMapping("/videos/delete/{videoId}")
    public ResponseEntity<ResponseMessage> deleteVideo() {
        final ResponseMessage message = new ResponseMessage(SUCCESS);
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "만든 템플릿으로 쇼츠 제작 신청")
    @PostMapping("/videos/{videoId}/create")
    public void createVideo() {
    }

    @Operation(summary = "만든 쇼츠 업로드")
    @PostMapping("/videos/{videoId}/upload")
    public void uploadShorts() {
    }

    @Operation(summary = "만든 쇼츠 다운로드")
    @PostMapping("/videos/{videoId}/download")
    public void downloadShorts() {
    }
}
