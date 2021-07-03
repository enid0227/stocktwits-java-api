package com.stocktwitlist.api.client;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.value.Symbol;
import com.stocktwitlist.api.value.TrendingResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class TrendingRequestTest {
  private static final Symbol SYMBOL_AAPL =
      Symbol.builder().setId(1L).setSymbol("AAPL").setTitle("Apple Inc").build();
  private static final Symbol SYMBOL_GOOG =
      Symbol.builder().setId(2L).setSymbol("GOOG").setTitle("Google Inc").build();

  private HttpClient mockHttpClient;
  private HttpResponse stubHttpResponse;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    mockHttpClient = mock(HttpClient.class);
    stubHttpResponse = mock(HttpResponse.class);
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void trendingSymbolsShouldReturnTrendingResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/trending/symbols.json?access_token=testToken";
    final TrendingResponse expectedResponse =
        TrendingResponse.create(ImmutableList.of(SYMBOL_AAPL, SYMBOL_GOOG));
    stubResponse(expectedResponse);

    TrendingResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .trending()
            .symbols()
            .send();

    assertThat(response).isEqualTo(expectedResponse);
    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
  }

  @Test
  public void trendingSymbolsEquitiesShouldReturnTrendingResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/trending/symbols/equities.json?access_token=testToken";
    final TrendingResponse expectedResponse =
        TrendingResponse.create(ImmutableList.of(SYMBOL_AAPL, SYMBOL_GOOG));
    stubResponse(expectedResponse);

    TrendingResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .trending()
            .symbolsEquities()
            .send();

    assertThat(response).isEqualTo(expectedResponse);
    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
  }

  @SuppressWarnings({"unchecked"}) // client template T is hidden in BodyHandlers
  private void stubResponse(TrendingResponse response) throws Exception {
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(response));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
  }

  private HttpRequest getActualHttpRequest() throws Exception {
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    return requestArgumentCaptor.getValue();
  }
}
