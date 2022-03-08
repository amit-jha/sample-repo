package com.aj.newsapi.service;

import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.util.NewsApiEverythingRequest;
import com.aj.newsapi.util.NewsApiHttpClient;
import com.aj.newsapi.util.NewsApiResponse;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * This class is responsible to get news articles based on <b>mode</b>.
 * <pre>
 *     mode can be either <b><i>online</i></b> or <b><i>offline</i></b>,
 *     default mode is online.
 *     mode can be changed by setting <b>application.mode.offline=true</b>
 * </pre>
 * see {@link NewsServiceImpl NewsServiceImpl}
 */
@Service
public class NewsServiceImpl implements NewsService{

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsServiceImpl.class);
    private NewsArticleResponse res;
    final private NewsApiHttpClient newsApiHttpClient;

    public NewsServiceImpl(NewsApiHttpClient newsApiHttpClient) {
        this.newsApiHttpClient = newsApiHttpClient;
    }

    @Override
    public NewsArticleResponse newsArticles(NewsArticleRequestBody requestBody) throws ApplicationException {
        if (requestBody == null)
            throw new ApplicationException("Request body is null");
        try {
            NewsApiEverythingRequest req = new NewsApiEverythingRequest.Builder().q(requestBody.getKeyword()).build();
            NewsApiResponse response = newsApiHttpClient.getResult(req);
            res = new NewsArticleResponse(response).build();
            return res.groupResults();
        }catch (ApplicationException e){
            LOGGER.error("Error while performing request");
            throw new ApplicationException(e);
        }
    }

}

