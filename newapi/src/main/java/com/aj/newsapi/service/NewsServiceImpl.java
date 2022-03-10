package com.aj.newsapi.service;

import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.exceptions.BusinessException;
import com.aj.newsapi.util.NewsApiEverythingRequest;
import com.aj.newsapi.util.NewsApiHttpClient;
import com.aj.newsapi.util.NewsApiResponse;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    @CircuitBreaker(name="newsArticleCircuitBreaker")
    public NewsArticleResponse newsArticles(NewsArticleRequestBody requestBody) throws ApplicationException, BusinessException {
        LOGGER.info("newsArticles() method started");
        if (requestBody == null)
            throw new BusinessException("Request body is null");
        try {
            NewsApiEverythingRequest req = new NewsApiEverythingRequest.Builder()
                    .q(requestBody.getKeyword())
//                    .from(buildFrom(requestBody.getInterval(), requestBody.getIntervalValue()))
//                    .to(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString())
                    .build();
            NewsApiResponse response = newsApiHttpClient.getResult(req);
            res = new NewsArticleResponse(response).build();
            LOGGER.info("newsArticles() method finished");
            res.groupResults();
            return res.groupResultsByIntervals(requestBody.getInterval(), requestBody.getIntervalValue());
        }catch (ApplicationException e){
            LOGGER.error("Error while performing request");
            throw new ApplicationException(e);
        }

    }

    public String buildFrom(String interval, int intervalValue) {
        LocalDateTime current_ts = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        String fromDate = "";
        switch (interval){
            case "years":
                fromDate =  current_ts.minusYears(intervalValue).truncatedTo(ChronoUnit.SECONDS).toString();
                break;
            case "months":
                fromDate =  current_ts.minusMonths(intervalValue).truncatedTo(ChronoUnit.SECONDS).toString();
                break;
            case "weeks":
                fromDate =  current_ts.minusWeeks(intervalValue).truncatedTo(ChronoUnit.SECONDS).toString();
                break;
            case "days":
                fromDate =  current_ts.minusDays(intervalValue).truncatedTo(ChronoUnit.SECONDS).toString();
                break;
            case "hours":
                fromDate =  current_ts.minusHours(intervalValue).truncatedTo(ChronoUnit.SECONDS).toString();
                break;
            case "minutes":
                fromDate =  current_ts.minusMinutes(intervalValue).toString();
                break;
            case "seconds":
                fromDate = current_ts.minusSeconds(intervalValue).toString();
                break;
        }
        return fromDate;
    }

}

