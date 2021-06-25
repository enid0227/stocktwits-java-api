package com.stocktwitlist.api.client;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.value.SearchResponse;
import com.stocktwitlist.api.value.SearchResult;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class StreamRequestTest {

  private HttpClient mockHttpClient;
  private HttpResponse stubHttpResponse;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    mockHttpClient = mock(HttpClient.class);
    stubHttpResponse = mock(HttpResponse.class);
    objectMapper =
        JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .addModule(new GuavaModule())
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"}) // client template T is hidden in BodyHandlers
  public void searchUsersShouldReturnSearchResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/search/users.json?access_token=testToken&q=jony";
    SearchResponse expectedResponse =
        SearchResponse.create(
            ImmutableList.of(
                SearchResult.builder()
                    .setId(1L)
                    .setType("user")
                    .setName("Jony")
                    .setUsername("jonymoney")
                    .setIdentity("User")
                    .setAvatarUrl("url1")
                    .setAvatarUrlSsl("url2")
                    .setClassification(ImmutableList.of("suggested"))
                    .build()));
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);

    SearchResponse response =
        StocktwitsApiClient.newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .search()
            .users("jony")
            .send();

    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    assertThat(requestArgumentCaptor.getValue().uri().toString()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"}) // client template T is hidden in BodyHandlers
  public void searchSymbolShouldReturnSearchResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/search/symbols.json?access_token=testToken&q=joy";
    SearchResponse expectedResponse =
        SearchResponse.create(
            ImmutableList.of(
                SearchResult.builder()
                    .setId(1L)
                    .setType("symbol")
                    .setSymbol("JOY")
                    .setTitle("Joy Global, Inc.")
                    .build()));
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);

    SearchResponse response =
        StocktwitsApiClient.newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .search()
            .symbols("joy")
            .send();

    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    assertThat(requestArgumentCaptor.getValue().uri().toString()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"}) // client template T is hidden in BodyHandlers
  public void searchIndexShouldReturnSearchResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/search.json?access_token=testToken&q=jo";
    SearchResponse expectedResponse =
        SearchResponse.create(
            ImmutableList.of(
                SearchResult.builder()
                    .setId(1L)
                    .setType("user")
                    .setName("Jony")
                    .setUsername("jonymoney")
                    .setIdentity("User")
                    .setAvatarUrl("url1")
                    .setAvatarUrlSsl("url2")
                    .setClassification(ImmutableList.of("suggested"))
                    .build(),
                SearchResult.builder()
                    .setId(1L)
                    .setType("symbol")
                    .setSymbol("JOY")
                    .setTitle("Joy Global, Inc.")
                    .build()));
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);

    SearchResponse response =
        StocktwitsApiClient.newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .search()
            .index("jo")
            .send();

    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    assertThat(requestArgumentCaptor.getValue().uri().toString()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }
}
