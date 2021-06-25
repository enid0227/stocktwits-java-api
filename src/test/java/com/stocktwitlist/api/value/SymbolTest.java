package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SymbolTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                Symbol.builder().setId(17L).setSymbol("JOY").setTitle("JOY Global, Inc.").build()))
        .isEqualTo("{\"id\":17,\"symbol\":\"JOY\",\"title\":\"JOY" + " Global, Inc.\"}");
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
