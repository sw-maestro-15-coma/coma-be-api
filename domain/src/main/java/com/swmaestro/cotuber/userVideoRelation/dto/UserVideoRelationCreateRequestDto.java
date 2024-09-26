package com.swmaestro.cotuber.userVideoRelation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserVideoRelationCreateRequestDto(@JsonProperty("youtube_url") String youtubeUrl) {

}
