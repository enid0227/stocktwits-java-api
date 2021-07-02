package com.stocktwitlist.api.client;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.value.Conversation;
import com.stocktwitlist.api.value.Cursor;
import com.stocktwitlist.api.value.Likes;
import com.stocktwitlist.api.value.Message;
import com.stocktwitlist.api.value.Source;
import com.stocktwitlist.api.value.StreamResponse;
import com.stocktwitlist.api.value.Symbol;
import com.stocktwitlist.api.value.User;
import com.stocktwitlist.api.value.Watchlist;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class StreamRequestTest {
  private static final Watchlist SAMPLE_WATCHLIST =
      Watchlist.builder()
          .setId(38398L)
          .setName("my picks")
          .setUpdatedAt(Instant.parse("2012-08-13T22:26:20Z"))
          .setCreatedAt(Instant.parse("2012-08-10T22:03:24Z"))
          .setSymbols(
              ImmutableList.of(
                  Symbol.builder().setId(7871L).setSymbol("FB").setTitle("Facebook").build(),
                  Symbol.builder().setId(2044L).setSymbol("GOOG").setTitle("Google Inc.").build()))
          .build();

  private static final User SAMPLE_USER =
      User.builder()
          .setAvatarUrl("https://avatars.png")
          .setAvatarUrlSsl("https://avatars.png")
          .setFollowers(3)
          .setFollowing(49)
          .setId(123456)
          .setIdeas(15)
          .setIdentity("User")
          .setJoinDate(LocalDate.of(2020, 07, 13))
          .setLikeCount(180)
          .setName("someuser")
          .setOfficial(false)
          .setPlusTier("")
          .setPremiumRoom("")
          .setTradeApp(false)
          .setUsername("someuser")
          .setWatchListStocksCount(138)
          .build();

  private static final Message SAMPLE_MESSAGE =
      Message.builder()
          .setId(987654321)
          .setBody("message body here")
          // LocalDateTime.of(2021, 03, 31, 20, 52, 40).toInstant(ZoneOffset.ofHours(0))
          .setCreatedAt(Instant.parse("2021-03-31T20:52:48Z"))
          .setUser(SAMPLE_USER)
          .setSource(
              Source.builder()
                  .setId(1234)
                  .setTitle("StockTwits for iOS")
                  .setUrl("http://www.stocktwits.com/mobile")
                  .build())
          .setConversation(
              Conversation.builder()
                  .setParent(false)
                  .setParentMessageId(987654320)
                  .setInReplyToMessageId(987654320)
                  .setReplies(3)
                  .build())
          .setLikes(
              Likes.builder()
                  .setTotal(3)
                  .setUserIds(ImmutableList.of(7654321L, 66554321L, 19198181L))
                  .build())
          .setSymbols(
              ImmutableList.of(
                  Symbol.builder()
                      .setId(1634)
                      .setSymbol("DRRX")
                      .setTitle("Durect Corp.")
                      .setIsFollowing(false)
                      .setHasPricing(true)
                      .setWatchlistCount(8673)
                      .build()))
          .setMentionedUsers(ImmutableList.of("@someuser2"))
          .setLikedBySelf(false)
          .setResharedBySelf(false)
          .build();

  private static final StreamResponse SAMPLE_STREAM_RESPONSE =
      StreamResponse.builder()
          .setCursor(Cursor.builder().setMax(9999L).setSince(10L).setMore(true).build())
          .setUser(SAMPLE_USER)
          .setWatchlist(SAMPLE_WATCHLIST)
          .setMessages(ImmutableList.of(SAMPLE_MESSAGE))
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
  public void streamUserWithIdShouldReturnStreamResponse() throws Exception {
    final long userId = 1234L;
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/user/" + userId + ".json?access_token=testToken";
    StreamResponse expectedResponse =
        SAMPLE_STREAM_RESPONSE.toBuilder()
            .setUser(SAMPLE_USER.toBuilder().setId(userId).build())
            .build();

    stubResponse(expectedResponse);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .user(userId)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void streamUserWithUsernameShouldReturnStreamResponse() throws Exception {
    final String username = "testusername";
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/user/"
            + username
            + ".json?access_token=testToken";
    StreamResponse expectedResponse =
        SAMPLE_STREAM_RESPONSE.toBuilder()
            .setUser(SAMPLE_USER.toBuilder().setUsername(username).build())
            .build();

    stubResponse(expectedResponse);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .user(username)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void streamSymbolWithIdShouldReturnStreamResponse() throws Exception {
    final long symbolId = 10L;
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/symbol/"
            + symbolId
            + ".json?access_token=testToken";
    StreamResponse expectedResponse =
        SAMPLE_STREAM_RESPONSE.toBuilder()
            .setSymbol(
                Symbol.builder().setSymbol("AAPL").setId(symbolId).setTitle("Apple Inc.").build())
            .build();

    stubResponse(expectedResponse);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .symbol(symbolId)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void streamSymbolWithSymbolNameShouldReturnStreamResponse() throws Exception {
    final String symbolName = "AAPL";
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/symbol/"
            + symbolName
            + ".json?access_token=testToken";
    StreamResponse expectedResponse =
        SAMPLE_STREAM_RESPONSE.toBuilder()
            .setSymbol(
                Symbol.builder().setSymbol(symbolName).setId(1L).setTitle("Apple Inc.").build())
            .build();

    stubResponse(expectedResponse);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .symbol(symbolName)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void streamFriendsShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/friends.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .friends()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamMentionsShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/mentions.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .mentions()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamDirectShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/direct.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .direct()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamDirectSentShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/direct_sent.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .directSent()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamDirectReceivedShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/direct_received.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .directReceived()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamWatchlistShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/watchlist.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .watchlist()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamAllShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/all.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .all()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamChartsShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/charts.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .charts()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamEquitiesShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/equities.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .equities()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamForexShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/forex.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .forex()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamFuturesShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/futures.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .futures()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamPrivateCompaniesShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/private_companies.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .private_companies()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamSuggestedShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/suggested.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .suggested()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamTrendingShouldReturnStreamResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/trending.json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .trending()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamSectorShouldReturnStreamResponse() throws Exception {
    final String sector = "technology";
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/sectors/"
            + sector
            + ".json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .sectors(sector)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  public void streamConversationShouldReturnStreamResponse() throws Exception {
    final long conversationId = 1234L;
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/conversation/"
            + conversationId
            + ".json?access_token=testToken";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .conversation(conversationId)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"}) // client template T is hidden in BodyHandlers
  public void streamSymbolsWithLessOrEqTenSymbolsShouldReturnStreamResponse() throws Exception {
    final ImmutableList<String> symbols = ImmutableList.of("AAPL", "MSFT");
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/streams/symbols.json?access_token=testToken&symbols=AAPL%2CMSFT";

    stubResponse(SAMPLE_STREAM_RESPONSE);

    StreamResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .streams()
            .symbols(symbols)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_STREAM_RESPONSE);
  }

  @SuppressWarnings({"unchecked", "rawtypes"}) // client template T is hidden in BodyHandlers
  private void stubResponse(StreamResponse response) throws Exception {
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(response));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
  }

  private String getActualUrl() throws Exception {
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    return requestArgumentCaptor.getValue().uri().toString();
  }
}
