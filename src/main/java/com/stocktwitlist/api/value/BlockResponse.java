package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonSerialize(as = BlockResponse.class)
public abstract class BlockResponse {
  @JsonCreator
  public static BlockResponse create(@JsonProperty("user") User user) {
    return new AutoValue_BlockResponse(user);
  }

  @JsonProperty("user")
  public abstract User user();
}
