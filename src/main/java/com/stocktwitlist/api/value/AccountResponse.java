package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonSerialize(as = AccountResponse.class)
public abstract class AccountResponse {
  @JsonCreator
  public static AccountResponse create(@JsonProperty("user") User user) {
    return new AutoValue_AccountResponse(user);
  }

  @JsonProperty("user")
  public abstract User user();
}
