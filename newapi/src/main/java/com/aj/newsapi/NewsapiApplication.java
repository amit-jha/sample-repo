package com.aj.newsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
@RefreshScope
@SpringBootApplication
public class NewsapiApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsapiApplication.class, args);
    }

}
