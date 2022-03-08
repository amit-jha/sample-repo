package com.aj.newsapi.exceptions;

public class NewsArticleException extends Exception{
    public NewsArticleException() {
    }

    public NewsArticleException(String message) {
        super(message);
    }

    public NewsArticleException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewsArticleException(Throwable cause) {
        super(cause);
    }
}
