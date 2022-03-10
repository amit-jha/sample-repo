package com.aj.newsapi.vo;

import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.util.NewsApiResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * The responsibility of this class to enhance/add features by decorating ArticleResponse
 * Implementation Details:
 *      Main class to represent Response
 *      Contract with external systems i.e UI
 *      This class uses
 *          Encapsulation using nested class
 *          Aggregation oops principles
 *          Decorator pattern to decorate ArticleResponse with interval details and interval grouping.
 *          Builder Pattern to build NewsArticle
 *
 */

@Getter
@Setter
public class NewsArticleResponse {

    @JsonIgnore
    private NewsApiResponse newsApiResponse;

    @JsonProperty("articles")
    private List<NewsArticle> newsArticles;

    @JsonProperty("totalResults")
    private int totalResults;

    @JsonProperty("groups")
    private Map<String, Map<String, List<NewsArticle>>> groupMap;

    @JsonProperty("status")
    private String status;

    @JsonProperty("errorMessage")
    private String errorMessage;


    public NewsArticleResponse() {
    }

    public NewsArticleResponse(NewsApiResponse newsApiResponse) {
        this.newsApiResponse = newsApiResponse;
        this.groupMap = new HashMap<>();
    }


    public NewsArticleResponse build() throws ApplicationException {
            if (this.newsApiResponse == null)
                throw new ApplicationException("Article response is null");

            this.totalResults = this.newsApiResponse.getTotalResults();
            this.newsArticles = this.newsApiResponse.getArticles()
                    .stream()
                    .map(article -> new NewsArticle.Builder()
                            .title(article.getTitle())
                            .author(article.getAuthor())
                            .description(article.getDescription())
                            .publishedAt(article.getPublishedAt())
                            .build()
                    )
                    .collect(Collectors.toList());
            return this;

    }

    /**
     *
     * @return
     */

    public NewsArticleResponse groupResults() {
        for (Interval interval: Interval.values()){
            Map<String, List<NewsArticle>> a = this.getNewsArticles()
                    .stream()
                    .collect(groupingBy(article -> article.intervalFields.get(interval.getName())));
            this.groupMap.put(interval.getName(), a);
        }
        return this;
    }

    public NewsArticleResponse groupResultsByIntervals(String interval, int intervalValue) {
        String key = "Last " + String.valueOf(intervalValue) +  " " + interval;
        Optional<Interval> interval1 = Arrays.stream(Interval.values()).filter(i -> i.getName().equals(interval)).findFirst();
        Instant lastInterval = Instant.now().truncatedTo(ChronoUnit.SECONDS).minus(Long.valueOf(intervalValue), interval1.get().getChronoUnit());
        List<NewsArticleResponse.NewsArticle> articles = this.getNewsArticles()
                    .stream()
                    .filter(newsArticle -> Instant.parse(newsArticle.publishedAt).isAfter(lastInterval))
                    .collect(Collectors.toList());
        this.groupMap.put("Input Grouped Results", Map.of(key, articles));
        return this;
    }




    /**
     * This class represents an article.
     */
    //TODO: intervalFields should not be part of this class.
    static class NewsArticle {

        @JsonProperty("title")
        private String title;

        @JsonProperty("publishedAt")
        private String publishedAt;

        @JsonProperty("author")
        private String author;

        @JsonProperty("description")
        private String description;

        @JsonProperty("intervalFields")
        private Map<String, String> intervalFields;


        public NewsArticle(NewsArticle.Builder builder) {
            this.title = builder.title;
            this.author = builder.author;
            this.description = builder.description;
            this.publishedAt = builder.publishedAt;
            this.intervalFields = builder.intervalFieldMap;

        }


        static class Builder {
            private String title;
            private String author;
            private String description;
            private String publishedAt;
            private Map<String, String> intervalFieldMap;


            public NewsArticle.Builder title(String title) {
                this.title = title;
                return this;
            }

            public NewsArticle.Builder author(String author) {
                this.author = author;
                return this;
            }

            public NewsArticle.Builder description(String description) {
                this.description = description;
                return this;
            }

            public NewsArticle.Builder publishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
                IntervalField publishedYear = PublishedDateTimeAnalyzer.getYear(publishedAt);
                IntervalField publishedMonth = PublishedDateTimeAnalyzer.getMonth(publishedAt);
                IntervalField publishedWeek = PublishedDateTimeAnalyzer.getWeek(publishedAt);
                IntervalField publishedDay = PublishedDateTimeAnalyzer.getDay(publishedAt);
                IntervalField publishedHour = PublishedDateTimeAnalyzer.getHour(publishedAt);
                IntervalField publishedMinute = PublishedDateTimeAnalyzer.getMinute(publishedAt);

                this.intervalFieldMap = Map.of(
                        publishedYear.getName(), publishedYear.getValue(),
                        publishedMonth.getName(), publishedMonth.getValue(),
                        publishedWeek.getName(), publishedWeek.getValue(),
                        publishedDay.getName(), publishedDay.getValue(),
                        publishedHour.getName(), publishedHour.getValue(),
                        publishedMinute.getName(), publishedMinute.getValue()
                );

                return this;
            }

            public NewsArticle build() {
                return new NewsArticle(this);
            }
        }


        /**
         * This class is decorating NewsArticles by adding
         * <p>publishedYear</p>
         * <p>publishedMonth</p>
         * <p>publishedDay</p>
         * <p>publishedHour</p>
         */
        //TODO: Try using Duration or Period class from java.util
        static class PublishedDateTimeAnalyzer {

            static IntervalField getYear(String publishedAt) {
                Supplier<String> s = () -> String.valueOf(OffsetDateTime
                        .parse(publishedAt)
                        .getYear());
                return new IntervalField(Interval.YEAR.getName(), s.get());
            }

            static IntervalField getMonth(String publishedAt) {
                Supplier<String> s = () -> String.valueOf(OffsetDateTime
                        .parse(publishedAt)
                        .getMonth().getValue());
                return new IntervalField(Interval.MONTH.getName(), s.get());
            }

            static IntervalField getWeek(String publishedAt) {
                Supplier<String> s = () -> String.valueOf(OffsetDateTime
                        .parse(publishedAt)
                        .get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
                return new IntervalField(Interval.WEEK.getName(), s.get());
            }

            static IntervalField getDay(String publishedAt) {
                Supplier<String> s = () -> String.valueOf(OffsetDateTime
                        .parse(publishedAt)
                        .getDayOfMonth());
                return new IntervalField(Interval.DAY.getName(), s.get());
            }

            static IntervalField getHour(String publishedAt) {
                Supplier<String> s = () -> String.valueOf(OffsetDateTime
                        .parse(publishedAt)
                        .getHour());
                return new IntervalField(Interval.HOUR.getName(), s.get());
            }

            static IntervalField getMinute(String publishedAt) {
                Supplier<String> s = () -> String.valueOf(OffsetDateTime
                        .parse(publishedAt)
                        .getMinute());
                return new IntervalField(Interval.MINUTE.getName(), s.get());
            }

        }
    }
}

@Getter
final class IntervalField {
    private final String name;
    private final String value;

    public IntervalField(String name, String value) {
        this.name = name;
        this.value = value;
    }
}

