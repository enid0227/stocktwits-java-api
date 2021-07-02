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
import com.stocktwitlist.api.value.Conversation;
import com.stocktwitlist.api.value.CreateMessageRequest;
import com.stocktwitlist.api.value.CreateMessageRequest.Sentiment;
import com.stocktwitlist.api.value.Likes;
import com.stocktwitlist.api.value.Message;
import com.stocktwitlist.api.value.MessageResponse;
import com.stocktwitlist.api.value.Source;
import com.stocktwitlist.api.value.Symbol;
import com.stocktwitlist.api.value.User;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class MessageRequestTest {
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
          .setId(10L)
          .setBody("message body here")
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

  private static final MessageResponse SAMPLE_MESSAGE_RESPONSE =
      MessageResponse.create(SAMPLE_MESSAGE);

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
  public void showMessageShouldReturnMessageResponse() throws Exception {
    final long messageId = 10L;
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/messages/show/10.json?access_token=testToken";
    stubResponse(SAMPLE_MESSAGE_RESPONSE);

    MessageResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .messages()
            .show(messageId)
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(SAMPLE_MESSAGE_RESPONSE);
  }

  @Test
  public void messageLikeShouldReturnMessageResponse() throws Exception {
    final long messageId = 10L;
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/messages/like.json?access_token=testToken";
    stubResponse(SAMPLE_MESSAGE_RESPONSE);

    MessageResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .messages()
            .like(messageId)
            .send();

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());

    RequestStringBodySubscriber subscriber = new RequestStringBodySubscriber();
    requestArgumentCaptor.getValue().bodyPublisher().get().subscribe(subscriber);

    assertThat(response).isEqualTo(SAMPLE_MESSAGE_RESPONSE);
    assertThat(requestArgumentCaptor.getValue().uri().toString()).isEqualTo(expectedUrl);
    assertThat(subscriber.getBodyString()).isEqualTo("{\"id\":\"10\"}");
  }

  @Test
  public void messageUnlikeShouldReturnMessageResponse() throws Exception {
    final long messageId = 15L;
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/messages/unlike.json?access_token=testToken";
    stubResponse(SAMPLE_MESSAGE_RESPONSE);

    MessageResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .messages()
            .unlike(messageId)
            .send();

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());

    RequestStringBodySubscriber subscriber = new RequestStringBodySubscriber();
    requestArgumentCaptor.getValue().bodyPublisher().get().subscribe(subscriber);

    assertThat(response).isEqualTo(SAMPLE_MESSAGE_RESPONSE);
    assertThat(requestArgumentCaptor.getValue().uri().toString()).isEqualTo(expectedUrl);
    assertThat(subscriber.getBodyString()).isEqualTo("{\"id\":\"15\"}");
  }

  @Test
  public void messageCreateShouldReturnMessageResponse() throws Exception {
    final CreateMessageRequest request =
        CreateMessageRequest.builder()
            .setInReplyToMessageId(1L)
            .setBody("to the moon")
            .setFileUrl("https://tothemoon.gif")
            .setSentiment(Sentiment.BULLISH)
            .build();
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/messages/create.json?access_token=testToken";
    stubResponse(SAMPLE_MESSAGE_RESPONSE);

    MessageResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .messages()
            .create(request)
            .send();

    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());

    RequestStringBodySubscriber subscriber = new RequestStringBodySubscriber();
    requestArgumentCaptor.getValue().bodyPublisher().get().subscribe(subscriber);

    assertThat(response).isEqualTo(SAMPLE_MESSAGE_RESPONSE);
    assertThat(requestArgumentCaptor.getValue().uri().toString()).isEqualTo(expectedUrl);
    assertThat(objectMapper.readValue(subscriber.getBodyString(), CreateMessageRequest.class))
        .isEqualTo(request);
  }

  @SuppressWarnings({"unchecked", "rawtypes"}) // client template T is hidden in BodyHandlers
  private void stubResponse(MessageResponse response) throws Exception {
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
