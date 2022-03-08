package com.aj.newsapi.service;

import com.aj.newsapi.NewsServiceConfiguration;
import com.aj.newsapi.exceptions.ApplicationException;
import com.aj.newsapi.util.NewsApiEverythingRequest;
import com.aj.newsapi.util.NewsApiHttpClient;
import com.aj.newsapi.util.NewsApiResponse;
import com.aj.newsapi.vo.NewsArticleRequestBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    NewsApiHttpClient client;
    NewsArticleRequestBody requestBody;
    NewsService newsService;

    @Mock
    NewsApiHttpClient mockHttpClient;

    @InjectMocks
    NewsServiceImpl mockNewsService;

    @Mock
    NewsApiEverythingRequest mockNewsApiEverythingRequest;

    @Mock
    NewsArticleRequestBody mockNewsArticleRequestBody;

    @Mock
    NewsApiEverythingRequest.Builder mockRequestBuilder;

    @Mock
    NewsApiResponse mockApiResponse;


    @Test
    public void testGetEverythingWithRequestBodyIsNull() {
        Exception exception = assertThrows(ApplicationException.class, () -> {
            mockNewsService.newsArticles(null);
        });
        String em = "Request body is null";
        String am = exception.getMessage();
        assertTrue(am.contains(em));
    }

    @Test
    public void testGetEverything() throws ApplicationException {
        newsService = new NewsServiceImpl(client);
        newsService.newsArticles(requestBody);

    }

    /*@Test
    public void testMockGetEverything() throws ApplicationException {
        when(mockNewsArticleRequestBody.getKeyword()).thenReturn("apple");
        when(mockRequestBuilder.q("apple")).thenReturn(mockRequestBuilder);
        when(mockRequestBuilder.build()).thenReturn(mockNewsApiEverythingRequest);
        when(mockHttpClient.getResult(mockNewsApiEverythingRequest)).thenReturn(mockApiResponse);

        mockNewsService.newsArticles(mockNewsArticleRequestBody);
        NewsArticleResponse c = mock(NewsArticleResponse.class, withSettings().useConstructor(mockApiResponse));
        when(c.build()).thenReturn(c);
        assertThat(c).isNotNull();

    }*/

    @BeforeEach
    void setUp() {
        NewsServiceConfiguration configuration = new NewsServiceConfiguration();
        configuration.setAccessKey("8e54ffdf1c474f819cdda99c0abe3f7b");
        client = new NewsApiHttpClient(configuration);
        requestBody = new NewsArticleRequestBody();
        requestBody.setKeyword("apple");


        NewsServiceConfiguration mockConfiguration = mock(NewsServiceConfiguration.class);
        mockConfiguration.setAccessKey(any(String.class));
        mockHttpClient = mock(NewsApiHttpClient.class, withSettings().useConstructor(configuration));
        mockNewsArticleRequestBody = mock(NewsArticleRequestBody.class);
        mockNewsArticleRequestBody.setKeyword("apple");
    }


    @AfterEach
    void tearDown() {
    }
}