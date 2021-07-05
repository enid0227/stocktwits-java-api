package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MuteResponse implements Response {
  @JsonCreator
  public static MuteResponse create(@JsonProperty("user") User user) {
    return new AutoValue_MuteResponse(user);
  }

  @JsonProperty("user")
  public abstract User user();
}
