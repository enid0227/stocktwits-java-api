package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FriendshipResponseTest {
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
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    FriendshipResponse response = FriendshipResponse.create(SAMPLE_USER);

    assertThat(objectMapper.writeValueAsString(response))
        .isEqualTo(
            "{\"user\":{\"avatar_url\":\"https://avatars.png\",\"avatar_url_ssl\":\"https://avatars.png\",\"followers\":3,\"following\":49,\"id\":123456,\"ideas\":15,\"identity\":\"User\",\"join_date\":\"2020-07-13\",\"like_count\":180,\"name\":\"someuser\",\"official\":false,\"plus_tier\":\"\",\"premium_room\":\"\",\"trade_app\":false,\"username\":\"someuser\",\"watchlist_stocks_count\":138}}");
  }

  @Test
  public void testDeserialization() throws Exception {
    FriendshipResponse response =
        objectMapper.readValue(
            "{\"user\":{\"avatar_url\":\"https://avatars.png\",\"avatar_url_ssl\":\"https://avatars.png\",\"followers\":3,\"following\":49,\"id\":123456,\"ideas\":15,\"identity\":\"User\",\"join_date\":\"2020-07-13\",\"like_count\":180,\"name\":\"someuser\",\"official\":false,\"plus_tier\":\"\",\"premium_room\":\"\",\"trade_app\":false,\"username\":\"someuser\",\"watchlist_stocks_count\":138}}",
            FriendshipResponse.class);

    assertThat(response).isEqualTo(FriendshipResponse.create(SAMPLE_USER));
  }
}
