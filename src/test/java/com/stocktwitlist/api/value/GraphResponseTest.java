package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphResponseTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
  }

  @Test
  public void testSerialization() throws Exception {
    GraphResponse response =
        GraphResponse.builder()
            .setCursor(Cursor.builder().setMax(8900L).setMore(true).setSince(78L).build())
            .setUsers(ImmutableList.of())
            .build();

    assertThat(objectMapper.writeValueAsString(response))
        .isEqualTo("{\"cursor\":{\"max\":8900,\"more\":true,\"since\":78},\"users\":[]}");
  }

  @Test
  public void testDeserialization() throws Exception {
    GraphResponse response =
        objectMapper.readValue(
            "{\"cursor\":{\"max\":8900,\"more\":true,\"since\":78},\"users\":[]}",
            GraphResponse.class);

    assertThat(response)
        .isEqualTo(
            GraphResponse.builder()
                .setCursor(Cursor.builder().setMax(8900L).setMore(true).setSince(78L).build())
                .setUsers(ImmutableList.of())
                .build());
  }
}
