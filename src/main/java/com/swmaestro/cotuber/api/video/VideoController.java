package com.swmaestro.cotuber.api.video;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Video", description = "비디오 관련 API")
@RequiredArgsConstructor
@RestController
public class VideoController {
    @Operation(summary = "대시보드의 동영상 목록 조회")
    @GetMapping("/videos")
    public void getVideos() {
    }

    @Operation(summary = "유튜브 링크로 동영상 업로드")
    @PostMapping("/videos")
    public void uploadVideo() {
    }

    @Operation(summary = "동영상 편집 상황 조회")
    @GetMapping("/videos/{videoId}")
    public void getVideo() {
    }

    @Operation(summary = "동영상 편집 상황 수정")
    @PostMapping("/videos/{videoId}")
    public void updateVideo() {
    }

    @Operation(summary = "동영상 삭제")
    @DeleteMapping("/videos/delete/{videoId}")
    public void deleteVideo() {
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
