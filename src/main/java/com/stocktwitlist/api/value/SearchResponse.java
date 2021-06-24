package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.contract.Response;

/** AutoValue of User Search JSON response. */
@AutoValue
@JsonSerialize(as = SearchResponse.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SearchResponse implements Response {
  @JsonCreator
  public static SearchResponse create(
      @JsonProperty("results") ImmutableList<SearchResult> results) {
    return new AutoValue_SearchResponse(results);
  }

  @JsonProperty("results")
  public abstract ImmutableList<SearchResult> results();
}
