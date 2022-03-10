package com.aj.newsapi;

import com.kwabenaberko.newsapilib.NewsApiClient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

/**
 * This class initialized beans, application configuration and
 * maps configuration property and
 *
 */
@Configuration
@ConfigurationProperties(prefix = "newsapi")
@Setter @Getter @ToString

public class NewsServiceConfiguration {
    private String buildVersion;
    private String accessKey;
    private String offlineMode;

    /**
     * newsapi client is bean gets created just after application initialized.
     * @return
     */

   @Bean
    public NewsApiClient initializeClient(){
       Assert.notNull(this.accessKey, "NewsApi key cannot be null");
       return new NewsApiClient(this.accessKey);
    }


}