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
import com.stocktwitlist.api.value.FriendshipResponse;
import com.stocktwitlist.api.value.User;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class FriendshipRequestContextTest {
  private static final User SAMPLE_USER =
      User.builder()
          .setId(5L)
          .setUsername("howardlindzon")
          .setName("Howard Lindzon")
          .setAvatarUrl("http://avatars.stocktwits.com/production/5/thumb-1314510834.png")
          .setAvatarUrlSsl("https://s3.amazonaws.com/st-avatars/production/5/thumb-1314510834.png")
          .setIdentity("User")
          .setClassification(ImmutableList.of("suggested"))
          .build();

  private static final FriendshipResponse SAMPLE_RESPONSE = FriendshipResponse.create(SAMPLE_USER);

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
  public void friendshipCreateShouldReturnFriendshipResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/friendships/create/5.json?access_token=testToken";
    setStubHttpResponse(SAMPLE_RESPONSE);

    FriendshipResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .friendships()
            .create(5L)
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(resposne).isEqualTo(SAMPLE_RESPONSE);
  }

  @Test
  public void friendshipDestroyShouldReturnFriendshipResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/friendships/destroy/6.json?access_token=testToken";
    FriendshipResponse expectedResponse =
        FriendshipResponse.create(SAMPLE_USER.toBuilder().setId(6L).build());
    setStubHttpResponse(expectedResponse);

    FriendshipResponse resposne =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .friendships()
            .destroy(6L)
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(resposne).isEqualTo(expectedResponse);
  }

  @SuppressWarnings({"unchecked"}) // client template T is hidden in BodyHandlers
  private void setStubHttpResponse(FriendshipResponse resposne) throws Exception {
    when(stubHttpResponse.statusCode()).thenReturn(200);
    when(stubHttpResponse.body()).thenReturn(objectMapper.writeValueAsString(resposne));
    when(mockHttpClient.send(any(HttpRequest.class), eq(BodyHandlers.ofString())))
        .thenReturn(stubHttpResponse);
  }

  private HttpRequest getActualHttpRequest() throws Exception {
    ArgumentCaptor<HttpRequest> requestArgumentCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    verify(mockHttpClient, times(1)).send(requestArgumentCaptor.capture(), any());
    return requestArgumentCaptor.getValue();
  }
}
