package com.aj.newsapi.controller;

import com.aj.newsapi.service.NewsService;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * This class is responsible to get news articles by delegating users request to
 * see {@link com.aj.newsapi.service.NewsService NewsService}
 */
@RestController
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/news")
    public NewsArticleResponse news(@RequestBody NewsArticleRequestBody requestBody) {
        NewsArticleResponse r =  newsService.getEverything(requestBody);
        return r;
    }

}
