package com.aj.newsapi.util;

import com.aj.newsapi.NewsServiceConfiguration;
import com.aj.newsapi.exceptions.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class NewsApiHttpClient {

    private static Logger LOGGER = LoggerFactory.getLogger(NewsApiHttpClient.class);

    private NewsServiceConfiguration configuration;
    private String apiKey;
    private static final String URL = "https://newsapi.org/v2/everything";

    @Autowired
    public NewsApiHttpClient(NewsServiceConfiguration configuration) {
        this.configuration = configuration;
        this.apiKey = this.configuration.getAccessKey();
    }

    public NewsApiResponse getResult(NewsApiEverythingRequest request) throws ApplicationException {
        try {
            URI uri = UriComponentsBuilder.fromUriString(URL)
                    .queryParam("apiKey", this.apiKey)
                    .queryParam("q", request.getQ())
                    .build()
                    .toUri();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();
            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return handleResponse(httpResponse.body());
        } catch (IOException e) {
            LOGGER.error("Error while performing http request");
            throw new ApplicationException(e);
        } catch (InterruptedException e) {
            LOGGER.error("Error while performing http request - Request might got interrupted");
            throw new ApplicationException(e);
        }
    }

    private NewsApiResponse handleResponse(String body) throws ApplicationException {
        ObjectMapper mapper = new ObjectMapper();
        NewsApiResponse response = null;
        try {
            response = mapper.readValue(body, new TypeReference<NewsApiResponse>(){});
        } catch (JsonProcessingException e) {
            LOGGER.error("Error while parsing response");
            throw new ApplicationException(e);
        }
        return response;

    }
}
