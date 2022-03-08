package com.aj.newsapi.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kwabenaberko.newsapilib.models.Source;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class NewsApiResponse {
    private String status;
    private int totalResults;
    private List<NewsArticle> articles;

    @Getter
    @Setter
    public static class NewsArticle{
        private String author;
        private String title;
        private String description;
        private String publishedAt;

        @JsonIgnore
        private String url;
        @JsonIgnore
        private String urlToImage;
        @JsonIgnore
        private Source source;
        @JsonIgnore
        private String content;
    }
}




