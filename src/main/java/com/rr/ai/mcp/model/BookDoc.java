package com.rr.ai.mcp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public record BookDoc(
        String key,
        String title,
        @JsonProperty("author_name") List<String> authorName,
        @JsonProperty("first_publish_year") Integer firstPublishYear,
        List<String> isbn,
        List<String> publisher,
        List<String> language,
        @JsonProperty("cover_i") Integer coverId
) {

}