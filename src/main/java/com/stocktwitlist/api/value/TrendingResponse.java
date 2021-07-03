package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonSerialize(as = TrendingResponse.class)
public abstract class TrendingResponse implements Response {
  @JsonCreator
  public static TrendingResponse create(@JsonProperty("symbols") ImmutableList<Symbol> symbols) {
    return new AutoValue_TrendingResponse(symbols);
  }

  @JsonProperty("symbols")
  public abstract ImmutableList<Symbol> symbols();
}
