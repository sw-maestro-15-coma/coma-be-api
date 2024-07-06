package com.swmaestro.cotuber.common;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "영상 다운로드, AI 처리 및 영상 처리 상태 Enum")
public enum ProgressState {
    @Schema(description = "전체 처리 완료")
    COMPLETE("complete"),
    @Schema(description = "에러 발생")
    ERROR("error"),
    @Schema(description = "유튜브 원본 영상 다운로드 중")
    YOUTUBE_DOWNLOADING("youtube_downloading"),
    @Schema(description = "영상 처리 서버에서 작업중")
    SHORTS_GENERATING("shorts_generating"),
    @Schema(description = "AI 서버에서 작업중")
    AI_PROCESSING("ai_processing")
    ;

    private final String state;

    ProgressState(String state) {
        this.state = state;
    }

    public String get() {
        return state;
    }
}
