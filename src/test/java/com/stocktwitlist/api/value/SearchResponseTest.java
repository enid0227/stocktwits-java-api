package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchResponseTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper =
        JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .addModule(new GuavaModule())
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();
  }

  @Test
  public void testDeserializeUserOnly() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                SearchResponse.create(
                    ImmutableList.of(
                        SearchResult.builder()
                            .setId(1L)
                            .setType("user")
                            .setName("Jony")
                            .setUsername("jonymoney")
                            .setIdentity("User")
                            .setAvatarUrl("url1")
                            .setAvatarUrlSsl("url2")
                            .setClassification(ImmutableList.of("suggested"))
                            .build()))))
        .isEqualTo(
            "{\"results\":[{\"avatar_url\":\"url1\",\"avatar_url_ssl\":\"url2\",\"classification\":[\"suggested\"],\"id\":1,\"identity\":\"User\",\"name\":\"Jony\",\"symbol\":null,\"title\":null,\"type\":\"user\",\"username\":\"jonymoney\"}]}");
  }

  @Test
  public void testDeserializeSymbolOnly() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                SearchResponse.create(
                    ImmutableList.of(
                        SearchResult.builder()
                            .setId(1L)
                            .setType("symbol")
                            .setSymbol("JOY")
                            .setTitle("Joy Global, Inc.")
                            .build()))))
        .isEqualTo(
            "{\"results\":[{\"avatar_url\":null,\"avatar_url_ssl\":null,\"classification\":null,\"id\":1,\"identity\":null,\"name\":null,\"symbol\":\"JOY\",\"title\":\"Joy Global, Inc.\",\"type\":\"symbol\",\"username\":null}]}");
  }

  @Test
  public void testDeserializeSymbolAndUser() throws Exception {
    assertThat(
            objectMapper.writeValueAsString(
                SearchResponse.create(
                    ImmutableList.of(
                        SearchResult.builder()
                            .setId(1L)
                            .setType("user")
                            .setName("Jony")
                            .setUsername("jonymoney")
                            .setIdentity("User")
                            .setAvatarUrl("url1")
                            .setAvatarUrlSsl("url2")
                            .setClassification(ImmutableList.of("suggested"))
                            .build(),
                        SearchResult.builder()
                            .setId(1L)
                            .setType("symbol")
                            .setSymbol("JOY")
                            .setTitle("Joy Global, Inc.")
                            .build()))))
        .isEqualTo(
            "{\"results\":[{\"avatar_url\":\"url1\",\"avatar_url_ssl\":\"url2\",\"classification\":[\"suggested\"],\"id\":1,\"identity\":\"User\",\"name\":\"Jony\",\"symbol\":null,\"title\":null,\"type\":\"user\",\"username\":\"jonymoney\"},{\"avatar_url\":null,\"avatar_url_ssl\":null,\"classification\":null,\"id\":1,\"identity\":null,\"name\":null,\"symbol\":\"JOY\",\"title\":\"Joy Global, Inc.\",\"type\":\"symbol\",\"username\":null}]}");
  }
}
