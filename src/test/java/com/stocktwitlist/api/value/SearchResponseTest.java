package com.stocktwitlist.api.value;

import static com.google.common.truth.Truth.assertThat;
import static com.stocktwitlist.api.helper.ObjectMappers.getDefaultTestObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchResponseTest {
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setupObjectMapper() {
    objectMapper = getDefaultTestObjectMapper();
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
            "{\"results\":[{\"avatar_url\":\"url1\",\"avatar_url_ssl\":\"url2\",\"classification\":[\"suggested\"],\"id\":1,\"identity\":\"User\",\"name\":\"Jony\",\"type\":\"user\",\"username\":\"jonymoney\"}]}");
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
            "{\"results\":[{\"id\":1,\"symbol\":\"JOY\",\"title\":\"Joy Global, Inc.\",\"type\":\"symbol\"}]}");
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
            "{\"results\":[{\"avatar_url\":\"url1\",\"avatar_url_ssl\":\"url2\",\"classification\":[\"suggested\"],\"id\":1,\"identity\":\"User\",\"name\":\"Jony\",\"type\":\"user\",\"username\":\"jonymoney\"},{\"id\":1,\"symbol\":\"JOY\",\"title\":\"Joy Global, Inc.\",\"type\":\"symbol\"}]}");
  }
}
