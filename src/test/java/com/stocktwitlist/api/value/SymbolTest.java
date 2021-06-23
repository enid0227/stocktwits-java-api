package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SymbolTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper =
        JsonMapper.builder().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).build();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                Symbol.builder().setId(17L).setSymbol("JOY").setTitle("JOY Global, Inc.").build()))
        .isEqualTo(
            "{\"has_pricing\":null,\"id\":17,\"is_following\":null,\"symbol\":\"JOY\",\"title\":\"JOY"
                + " Global, Inc.\",\"watchlist_count\":null}");
  }

  @Test
  public void testDeserialization() throws Exception {
    assertThat(
            objectMapper.readValue(
                "{\"id\":17,\"symbol\":\"JOY\",\"title\":\"JOY Global, Inc.\"}", Symbol.class))
        .isEqualTo(
            Symbol.builder().setId(17L).setSymbol("JOY").setTitle("JOY Global, Inc.").build());
  }
}
