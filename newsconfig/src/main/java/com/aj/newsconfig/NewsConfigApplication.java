package com.aj.newsconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class NewsConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsConfigApplication.class, args);
    }

}
