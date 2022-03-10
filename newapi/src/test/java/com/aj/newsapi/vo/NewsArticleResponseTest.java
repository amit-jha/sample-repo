package com.aj.newsapi.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewsArticleResponseTest {


    @Test
    void groupResults() {
    }

    @Test
    void groupResultsByIntervals() {
        NewsArticleResponse res = new NewsArticleResponse();
        res.groupResultsByIntervals("hours", 5);
    }

}