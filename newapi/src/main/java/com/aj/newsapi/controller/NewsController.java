package com.aj.newsapi.controller;

import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.service.NewsService;
import com.aj.newsapi.service.NewsServiceImpl;
import com.aj.newsapi.util.NewsApiResponse;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class is responsible to get news articles by delegating users request to
 * see {@link NewsServiceImpl NewsServiceImpl}
 */
@RestController
public class NewsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path = "/news")
    public NewsArticleResponse news(@RequestBody NewsArticleRequestBody requestBody) {
        try {
            NewsArticleResponse r = newsService.newsArticles(requestBody);
            return r;
        }catch (ApplicationException ae){
            //TODO: Generic response can be passed.
            LOGGER.error("Error while processing request");
            return new NewsArticleResponse(new NewsApiResponse());
        }

    }

}
