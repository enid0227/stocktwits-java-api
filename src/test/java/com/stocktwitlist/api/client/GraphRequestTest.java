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
import com.stocktwitlist.api.value.Cursor;
import com.stocktwitlist.api.value.GraphResponse;
import com.stocktwitlist.api.value.User;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class GraphRequestTest {
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

  private static final GraphResponse SAMPLE_GRAPH_RESPONSE =
      GraphResponse.builder()
          .setCursor(Cursor.builder().setMax(100L).setSince(1L).setMore(true).build())
          .setUsers(ImmutableList.of(SAMPLE_USER))
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
  public void graphBlockingShouldReturnGraphResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/graph/blocking.json?access_token=testToken";
    setStubHttpResponse(SAMPLE_GRAPH_RESPONSE);

    GraphResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .graph()
            .blocking()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(resposne).isEqualTo(SAMPLE_GRAPH_RESPONSE);
  }

  @Test
  public void graphMutingShouldReturnGraphResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/graph/muting.json?access_token=testToken";
    setStubHttpResponse(SAMPLE_GRAPH_RESPONSE);

    GraphResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .graph()
            .muting()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(resposne).isEqualTo(SAMPLE_GRAPH_RESPONSE);
  }

  @Test
  public void graphFollowingShouldReturnGraphResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/graph/following.json?access_token=testToken";
    setStubHttpResponse(SAMPLE_GRAPH_RESPONSE);

    GraphResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .graph()
            .following()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(resposne).isEqualTo(SAMPLE_GRAPH_RESPONSE);
  }

  @Test
  public void graphFollowersShouldReturnGraphResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/graph/followers.json?access_token=testToken";
    setStubHttpResponse(SAMPLE_GRAPH_RESPONSE);

    GraphResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .graph()
            .followers()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(resposne).isEqualTo(SAMPLE_GRAPH_RESPONSE);
  }

  @Test
  public void graphSymbolsShouldReturnGraphResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/graph/symbols.json?access_token=testToken";
    setStubHttpResponse(SAMPLE_GRAPH_RESPONSE);

    GraphResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .graph()
            .symbols()
            .send();

    assertThat(getActualUrl()).isEqualTo(expectedUrl);
    assertThat(resposne).isEqualTo(SAMPLE_GRAPH_RESPONSE);
  }

  @SuppressWarnings({"unchecked"}) // client template T is hidden in BodyHandlers
  private void setStubHttpResponse(GraphResponse resposne) throws Exception {
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(resposne));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
  }

  private String getActualUrl() throws Exception {
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    return requestArgumentCaptor.getValue().uri().toString();
  }
}
