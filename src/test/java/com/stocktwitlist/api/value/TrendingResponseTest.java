package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrendingResponseTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    TrendingResponse response =
        TrendingResponse.create(
            ImmutableList.of(
                Symbol.builder().setId(3000).setSymbol("AAPL").setTitle("list1").build()));

    assertThat(objectMapper.writeValueAsString(response))
        .isEqualTo("{\"symbols\":[{\"id\":3000,\"symbol\":\"AAPL\",\"title\":\"list1\"}]}");
  }

  @Test
  public void testDeserialization() throws Exception {
    TrendingResponse response =
        objectMapper.readValue(
            "{\"symbols\":[{\"id\":3000,\"symbol\":\"AAPL\",\"title\":\"list1\"}]}",
            TrendingResponse.class);

    assertThat(response)
        .isEqualTo(
            TrendingResponse.create(
                ImmutableList.of(
                    Symbol.builder().setId(3000).setSymbol("AAPL").setTitle("list1").build())));
  }
}
