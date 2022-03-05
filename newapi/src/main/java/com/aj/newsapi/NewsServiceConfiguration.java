package com.aj.newsapi;

import com.kwabenaberko.newsapilib.NewsApiClient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class maps configuration property
 *
 */
@Configuration
@ConfigurationProperties(prefix = "newsapi")
@Setter @Getter @ToString
public class NewsServiceConfiguration {
    private String buildVersion;
    private String accessKey;
    private String offlineMode;


   @Bean
    public NewsApiClient initializeClient(){
       return new NewsApiClient(this.accessKey);
    }

}
