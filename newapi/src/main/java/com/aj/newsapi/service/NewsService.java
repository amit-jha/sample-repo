package com.aj.newsapi.service;

import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.exceptions.BusinessException;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import com.aj.newsapi.vo.NewsArticleResponse;

public interface NewsService {
    NewsArticleResponse newsArticles(NewsArticleRequestBody newsArticleRequestBody) throws ApplicationException, BusinessException;
}
