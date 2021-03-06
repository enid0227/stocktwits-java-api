package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
  private static final String SAMPLE_JSON =
      "{\"avatar_url\":\"https://avatars.png\",\"avatar_url_ssl\":\"https://avatars.png\",\"classification\":[],\"followers\":3,\"following\":49,\"id\":123456,\"ideas\":15,\"identity\":\"User\",\"join_date\":\"2020-07-13\",\"like_count\":180,\"name\":\"someuser\",\"official\":false,\"plus_tier\":\"\",\"premium_room\":\"\",\"trade_app\":false,\"username\":\"someuser\",\"watchlist_stocks_count\":138}";

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
          .setClassification(ImmutableList.of())
          .setWatchListStocksCount(138)
          .build();

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    // TODO: migrate objectMapper to abstract class or junit extension
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(objectMapper.writeValueAsString(SAMPLE_USER)).isEqualTo(SAMPLE_JSON);
  }

  @Test
  public void testDeserialization() throws Exception {
    assertThat(objectMapper.readValue(SAMPLE_JSON, User.class)).isEqualTo(SAMPLE_USER);
  }
}
