package com.swmaestro.cotuber.draft.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DraftCreateRequestDto(@JsonProperty("youtube_url") String youtubeUrl) {

}
