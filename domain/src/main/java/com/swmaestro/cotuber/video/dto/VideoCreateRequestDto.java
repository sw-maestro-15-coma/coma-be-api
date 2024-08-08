package com.swmaestro.cotuber.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VideoCreateRequestDto(@JsonProperty("youtube_url") String youtubeUrl) {

}
