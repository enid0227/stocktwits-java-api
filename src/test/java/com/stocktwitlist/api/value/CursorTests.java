package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CursorTests {

  ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                Cursor.builder().setMax(10L).setSince(1L).setMore(true).build()))
        .isEqualTo("{\"max\":10,\"more\":true,\"since\":1}");
  }

  @Test
  public void testDeserialization() throws Exception {
    assertThat(objectMapper.readValue("{\"max\":10,\"more\":true,\"since\":1}", Cursor.class))
        .isEqualTo(Cursor.builder().setMax(10L).setSince(1L).setMore(true).build());
  }
}
