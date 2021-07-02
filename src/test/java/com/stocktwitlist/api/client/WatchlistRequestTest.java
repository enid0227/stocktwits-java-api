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
import com.stocktwitlist.api.helper.RequestStringBodySubscriber;
import com.stocktwitlist.api.value.Symbol;
import com.stocktwitlist.api.value.Watchlist;
import com.stocktwitlist.api.value.WatchlistResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class WatchlistRequestTest {
  private static final Symbol SYMBOL_AAPL =
      Symbol.builder().setId(1L).setSymbol("AAPL").setTitle("Apple Inc").build();
  private static final Symbol SYMBOL_GOOG =
      Symbol.builder().setId(2L).setSymbol("GOOG").setTitle("Google Inc").build();
  private static final Symbol SYMBOL_BAC =
      Symbol.builder().setId(3L).setSymbol("BAC").setTitle("Bank of America Corporation").build();
  private static final Symbol SYMBOL_GS =
      Symbol.builder().setId(4L).setSymbol("GS").setTitle("Goldman Sachs Group, Inc.").build();
  private static final Watchlist WATCHLIST_TECH =
      Watchlist.builder()
          .setId(1L)
          .setName("Tech Watchlist")
          .setCreatedAt(Instant.parse("2021-06-26T02:03:39Z"))
          .setUpdatedAt(Instant.parse("2021-06-28T02:03:39Z"))
          .setSymbols(ImmutableList.of(SYMBOL_AAPL, SYMBOL_GOOG))
          .build();
  private static final Watchlist WATCHLIST_FINANCE =
      Watchlist.builder()
          .setId(2L)
          .setName("Finance Watchlist")
          .setCreatedAt(Instant.parse("2021-06-26T02:03:39Z"))
          .setUpdatedAt(Instant.parse("2021-06-27T02:03:39Z"))
          .setSymbols(ImmutableList.of(SYMBOL_BAC, SYMBOL_GS))
          .build();

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
  public void watchlistIndexShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder()
            .setWatchlists(ImmutableList.of(WATCHLIST_TECH, WATCHLIST_FINANCE))
            .build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .index()
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
  }

  @Test
  public void watchlistCreateShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists/create.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder().setWatchlist(WATCHLIST_TECH).build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .create(WATCHLIST_TECH.name())
            .send();

    RequestStringBodySubscriber rbs = new RequestStringBodySubscriber();
    HttpRequest actualRequest = getActualHttpRequest();
    actualRequest.bodyPublisher().get().subscribe(rbs);
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(rbs.getBodyString()).isEqualTo("{\"name\":\"Tech Watchlist\"}");
  }

  @Test
  public void watchlistUpdateShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists/update/2.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder().setWatchlist(WATCHLIST_FINANCE).build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .update(WATCHLIST_FINANCE.id(), WATCHLIST_FINANCE.name())
            .send();

    RequestStringBodySubscriber rbs = new RequestStringBodySubscriber();
    HttpRequest actualRequest = getActualHttpRequest();
    actualRequest.bodyPublisher().get().subscribe(rbs);
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(rbs.getBodyString()).isEqualTo("{\"name\":\"Finance Watchlist\"}");
  }

  @Test
  public void watchlistDestroyShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists/destroy/2.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder().setWatchlist(WATCHLIST_FINANCE).build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .destroy(WATCHLIST_FINANCE.id())
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
  }

  @Test
  public void watchlistShowShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists/show/1.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder().setWatchlist(WATCHLIST_TECH).build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .show(WATCHLIST_TECH.id())
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.method()).isEqualTo("GET");
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
  }

  @Test
  public void watchlistSymbolsCreateShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists/1/symbols/create.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder().setSymbols(ImmutableList.of(SYMBOL_AAPL, SYMBOL_GOOG)).build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .symbolsCreate(
                WATCHLIST_TECH.id(), ImmutableList.of(SYMBOL_AAPL.symbol(), SYMBOL_GOOG.symbol()))
            .send();

    RequestStringBodySubscriber rbs = new RequestStringBodySubscriber();
    HttpRequest actualRequest = getActualHttpRequest();
    actualRequest.bodyPublisher().get().subscribe(rbs);
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(rbs.getBodyString()).isEqualTo("{\"symbols\":\"AAPL,GOOG\"}");
  }

  @Test
  public void watchlistSymbolsDestroyShouldReturnWatchlistResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/watchlists/1/symbols/destroy.json?access_token=testToken";
    WatchlistResponse expectedResponse =
        WatchlistResponse.builder().setSymbols(ImmutableList.of(SYMBOL_AAPL, SYMBOL_GOOG)).build();
    stubResponse(expectedResponse);

    WatchlistResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .watchlists()
            .symbolsDestroy(
                WATCHLIST_TECH.id(), ImmutableList.of(SYMBOL_AAPL.symbol(), SYMBOL_GOOG.symbol()))
            .send();

    RequestStringBodySubscriber rbs = new RequestStringBodySubscriber();
    HttpRequest actualRequest = getActualHttpRequest();
    actualRequest.bodyPublisher().get().subscribe(rbs);
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(rbs.getBodyString()).isEqualTo("{\"symbols\":\"AAPL,GOOG\"}");
  }

  @SuppressWarnings({"unchecked"}) // client template T is hidden in BodyHandlers
  private void stubResponse(WatchlistResponse response) throws Exception {
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
