package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import java.time.Instant;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StreamResponseTest {
  private static final String SAMPLE_JSON =
      "{\"cursor\":{\"max\":9999,\"more\":true,\"since\":10},\"messages\":[{\"body\":\"message body"
          + " here\",\"conversation\":{\"in_reply_to_message_id\":987654320,\"parent\":false,\"parent_message_id\":987654320,\"replies\":3},\"created_at\":\"2021-03-31T20:52:48Z\",\"id\":987654321,\"liked_by_self\":false,\"likes\":{\"total\":3,\"user_ids\":[7654321,66554321,19198181]},\"mentioned_users\":[\"@someuser2\"],\"reshared_by_self\":false,\"source\":{\"id\":1234,\"title\":\"StockTwits"
          + " for iOS\",\"url\":\"http://www.stocktwits.com/mobile\"},\"symbols\":[{\"has_pricing\":true,\"id\":1634,\"is_following\":false,\"symbol\":\"DRRX\",\"title\":\"Durect"
          + " Corp.\",\"watchlist_count\":8673}],\"user\":{\"avatar_url\":\"https://avatars.png\",\"avatar_url_ssl\":\"https://avatars.png\",\"followers\":3,\"following\":49,\"id\":123456,\"ideas\":15,\"identity\":\"User\",\"join_date\":\"2020-07-13\",\"like_count\":180,\"name\":\"someuser\",\"official\":false,\"plus_tier\":\"\",\"premium_room\":\"\",\"trade_app\":false,\"username\":\"someuser\",\"watchlist_stocks_count\":138}}],\"user\":{\"avatar_url\":\"https://avatars.png\",\"avatar_url_ssl\":\"https://avatars.png\",\"followers\":3,\"following\":49,\"id\":123456,\"ideas\":15,\"identity\":\"User\",\"join_date\":\"2020-07-13\",\"like_count\":180,\"name\":\"someuser\",\"official\":false,\"plus_tier\":\"\",\"premium_room\":\"\",\"trade_app\":false,\"username\":\"someuser\",\"watchlist_stocks_count\":138},\"watchlist\":{\"created_at\":\"2012-08-10T22:03:24Z\",\"id\":38398,\"name\":\"my"
          + " picks\",\"symbols\":[{\"id\":7871,\"symbol\":\"FB\",\"title\":\"Facebook\"},{\"id\":2044,\"symbol\":\"GOOG\",\"title\":\"Google"
          + " Inc.\"}],\"updated_at\":\"2012-08-13T22:26:20Z\"}}";

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

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper =
        JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .addModule(new GuavaModule())
            .serializationInclusion(Include.NON_NULL)
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .build();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(objectMapper.writeValueAsString(SAMPLE_STREAM_RESPONSE)).isEqualTo(SAMPLE_JSON);
  }

  @Test
  public void testDeserialization() throws Exception {
    assertThat(objectMapper.readValue(SAMPLE_JSON, StreamResponse.class))
        .isEqualTo(SAMPLE_STREAM_RESPONSE);
  }
}
