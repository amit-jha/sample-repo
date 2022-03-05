package com.aj.newsapi.service;

import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is responsible to get news articles based on <b>mode</b>.
 * <pre>
 *     mode can be either <b><i>online</i></b> or <b><i>offline</i></b>,
 *     default mode is online.
 *     mode can be changed by setting <b>application.mode.offline=true</b>
 * </pre>
 * see {@link com.aj.newsapi.service.NewsService NewsService}
 */
@Service
public class NewsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsService.class);


    final private NewsApiClient newsApiClient;
    private NewsArticleResponse res;

    @Autowired
    public NewsService(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;

    }

    public NewsArticleResponse getEverything(NewsArticleRequestBody requestBody) {
        LOGGER.debug("Calling getEverything");
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(requestBody.getKeyword())
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        res = new NewsArticleResponse(response).build();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );

        return res.groupResults();

    }

//    public NewsArticleResponse getEverything() {
//        LOGGER.debug("Calling getEverything");
//        newsApiClient.getEverything(
//                new EverythingRequest.Builder()
//                        .q("apple")
//                        .build(),
//                new NewsApiClient.ArticlesResponseCallback() {
//                    @Override
//                    public void onSuccess(ArticleResponse response) {
//                        res = new NewsArticleResponse(response).build();
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        System.out.println(throwable.getMessage());
//                    }
//                }
//        );
//
//
//        return res.groupResults();
//    }


}
