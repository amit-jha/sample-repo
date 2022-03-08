package com.aj.newsapi.controller;

import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.service.NewsService;
import com.aj.newsapi.service.NewsServiceImpl;
import com.aj.newsapi.util.NewsApiResponse;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Get news articles based on query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "News articles retrieved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NewsArticleResponse.class )) }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "News articles not found",
                    content = @Content) })
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
