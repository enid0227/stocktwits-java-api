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
import com.stocktwitlist.api.value.Conversation;
import com.stocktwitlist.api.value.Cursor;
import com.stocktwitlist.api.value.DeletionResponse;
import com.stocktwitlist.api.value.Likes;
import com.stocktwitlist.api.value.Message;
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

public class DeletionRequestTest {
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
  public void deletionUsersShouldReturnDeletionResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/deletions/users.json?access_token=testToken&limit=25&max=100&since=1";

    DeletionResponse expectedResponse =
        DeletionResponse.builder()
            .setCursor(Cursor.builder().setSince(1L).setMax(100L).setMore(true).build())
            .setUsers(ImmutableList.of(SAMPLE_USER))
            .build();
    setStubHttpResponse(expectedResponse);

    DeletionResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .deletions()
            .setSince(1L)
            .setMax(100L)
            .setLimit(25)
            .users()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void deletionMessagesShouldReturnDeletionResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/deletions/messages.json?access_token=testToken&limit=25&max=100&since=1";

    DeletionResponse expectedResponse =
        DeletionResponse.builder()
            .setCursor(Cursor.builder().setSince(1L).setMax(100L).setMore(true).build())
            .setMessages(ImmutableList.of(SAMPLE_MESSAGE))
            .build();

    setStubHttpResponse(expectedResponse);

    DeletionResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .deletions()
            .setSince(1L)
            .setMax(100L)
            .setLimit(25)
            .messages()
            .send();
    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  private void setStubHttpResponse(DeletionResponse expectedResponse) throws Exception {
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(expectedResponse));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
  }

  private String getActualUrl() throws Exception {
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    return requestArgumentCaptor.getValue().uri().toString();
  }
}
