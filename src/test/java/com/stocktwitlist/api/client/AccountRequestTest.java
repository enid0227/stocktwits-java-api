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
import com.stocktwitlist.api.helper.RequestStringBodySubscriber;
import com.stocktwitlist.api.value.AccountResponse;
import com.stocktwitlist.api.value.AccountUpdateRequest;
import com.stocktwitlist.api.value.User;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

public class AccountRequestTest {
  private static final User USER_JIMMY =
      User.builder()
          .setId(176389L)
          .setUsername("jimmychanos")
          .setName("Jimmy Chanos")
          .setAvatarUrl("http://avatars.stocktwits.com/images/default_avatar_thumb.jpg")
          .setAvatarUrlSsl("https://avatars.stocktwits.com/images/default_avatar_thumb.jpg")
          .setIdentity("User")
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
  public void accountVerifyShouldReturnAccountResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/account/verify.json?access_token=testToken";
    AccountResponse expectedResponse = AccountResponse.create(USER_JIMMY);
    stubResponse(expectedResponse);

    AccountResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .account()
            .verify()
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void accountUpdateShouldReturnAccountResponse() throws Exception {
    final String expectedUrl =
        "https://api.stocktwits.com/api/2/account/verify.json?access_token=testToken";
    AccountResponse expectedResponse = AccountResponse.create(USER_JIMMY);
    stubResponse(expectedResponse);

    AccountResponse response =
        new StocktwitsClient()
            .newRequest("testToken")
            .setHttpClient(mockHttpClient)
            .account()
            .update(
                AccountUpdateRequest.builder()
                    .setName("John Doe")
                    .setUsername("johndoe")
                    .setEmail("johndoe@gmail.com")
                    .build())
            .send();

    HttpRequest actualRequest = getActualHttpRequest();
    RequestStringBodySubscriber rbs = new RequestStringBodySubscriber();
    actualRequest.bodyPublisher().get().subscribe(rbs);
    assertThat(actualRequest.uri().toString()).isEqualTo(expectedUrl);
    assertThat(actualRequest.method()).isEqualTo("POST");
    assertThat(response).isEqualTo(expectedResponse);
    assertThat(rbs.getBodyString())
        .isEqualTo(
            "{\"name\":\"John Doe\",\"email\":\"johndoe@gmail.com\",\"username\":\"johndoe\"}");
  }

  @SuppressWarnings({"unchecked"}) // client template T is hidden in BodyHandlers
  private void stubResponse(AccountResponse response) throws Exception {
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
