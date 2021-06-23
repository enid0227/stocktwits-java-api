package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SourceTest {

  ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    objectMapper =
        JsonMapper.builder().configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true).build();
  }

  @Test
  public void testSerialization() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                Source.builder().setId(1L).setTitle("stocktwits").setUrl("stocktwits.com").build()))
        .isEqualTo("{\"id\":1,\"title\":\"stocktwits\",\"url\":\"stocktwits.com\"}");
  }

  @Test
  public void testDeserialization() throws Exception {
    assertThat(
            objectMapper.readValue(
                "{\"id\":1,\"title\":\"stocktwits\",\"url\":\"stocktwits.com\"}", Source.class))
        .isEqualTo(
            Source.builder().setId(1L).setTitle("stocktwits").setUrl("stocktwits.com").build());
  }
}
