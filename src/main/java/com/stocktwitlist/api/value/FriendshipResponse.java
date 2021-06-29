package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonSerialize(as = FriendshipResponse.class)
public abstract class FriendshipResponse {
  @JsonCreator
  public static FriendshipResponse create(@JsonProperty("user") User user) {
    return new AutoValue_FriendshipResponse(user);
  }

  @JsonProperty("user")
  public abstract User user();
}
