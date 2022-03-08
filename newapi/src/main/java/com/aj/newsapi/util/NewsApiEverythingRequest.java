package com.aj.newsapi.util;

import lombok.Getter;

@Getter
public class NewsApiEverythingRequest {
    private String q;
    private String sources;
    private String domains;
    private String from;
    private String to;
    private String language;
    private String sortBy;
    private String pageSize;
    private String page;

    private NewsApiEverythingRequest(NewsApiEverythingRequest.Builder builder) {
        this.q = builder.q;
        this.sources = builder.sources;
        this.domains = builder.domains;
        this.from = builder.from;
        this.to = builder.to;
        this.language = builder.language;
        this.sortBy = builder.sortBy;
        this.pageSize = builder.pageSize;
        this.page = builder.page;
    }

    public static class Builder {
        private String q;
        private String sources;
        private String domains;
        private String from;
        private String to;
        private String language;
        private String sortBy;
        private String pageSize;
        private String page;

        public Builder() {
        }

        public NewsApiEverythingRequest.Builder q(String q) {
            this.q = q;
            return this;
        }

        public NewsApiEverythingRequest.Builder sources(String sources) {
            this.sources = sources;
            return this;
        }

        public NewsApiEverythingRequest.Builder domains(String domains) {
            this.domains = domains;
            return this;
        }

        public NewsApiEverythingRequest.Builder from(String from) {
            this.from = from;
            return this;
        }

        public NewsApiEverythingRequest.Builder to(String to) {
            this.to = to;
            return this;
        }

        public NewsApiEverythingRequest.Builder language(String language) {
            this.language = language;
            return this;
        }

        public NewsApiEverythingRequest.Builder sortBy(String sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public NewsApiEverythingRequest.Builder pageSize(int pageSize) {
            this.pageSize = String.valueOf(pageSize);
            return this;
        }

        public NewsApiEverythingRequest.Builder page(int page) {
            this.page = String.valueOf(page);
            return this;
        }

        public NewsApiEverythingRequest build() {
            return new NewsApiEverythingRequest(this);
        }
    }

}
