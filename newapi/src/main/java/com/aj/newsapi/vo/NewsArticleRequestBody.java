package com.aj.newsapi.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsArticleRequestBody {

    @JsonProperty(value = "keyword")
    private String keyword;

    @JsonProperty(value = "interval")
    private String interval;

    @JsonProperty(value = "intervalValue")
    private int intervalValue;

    @JsonIgnore
    private String url;

}
