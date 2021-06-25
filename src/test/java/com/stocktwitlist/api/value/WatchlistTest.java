package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WatchlistTest {
  private static final String SAMPLE_JSON =
      "{\"created_at\":\"2012-08-10T22:03:24Z\",\"id\":38398,\"name\":\"my"
          + " picks\",\"symbols\":[{\"id\":7871,\"symbol\":\"FB\",\"title\":\"Facebook\"},{\"id\":2044,\"symbol\":\"GOOG\",\"title\":\"Google"
          + " Inc.\"}],\"updated_at\":\"2012-08-13T22:26:20Z\"}";

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

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(objectMapper.writeValueAsString(SAMPLE_WATCHLIST)).isEqualTo(SAMPLE_JSON);
  }

  @Test
  public void testDeserialization() throws Exception {
    assertThat(objectMapper.readValue(SAMPLE_JSON, Watchlist.class)).isEqualTo(SAMPLE_WATCHLIST);
  }
}
