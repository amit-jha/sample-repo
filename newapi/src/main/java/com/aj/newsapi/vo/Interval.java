package com.aj.newsapi.vo;

import lombok.Getter;

import java.time.temporal.ChronoUnit;

@Getter
public enum Interval {
    YEAR("years", "2022", ChronoUnit.YEARS),
    MONTH("months", "1", ChronoUnit.MONTHS),
    WEEK("weeks", "1", ChronoUnit.WEEKS),
    DAY("days", "1", ChronoUnit.DAYS),
    HOUR("hours", "", ChronoUnit.HOURS),
    MINUTE("minutes", "", ChronoUnit.MINUTES);


    private String name;
    private String defaultValue;
    private ChronoUnit chronoUnit;

    private Interval(String name, String defaultValue, ChronoUnit chronoUnit) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.chronoUnit = chronoUnit;
    }


}