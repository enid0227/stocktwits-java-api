package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/** Request parameter for Account update endpoint */
@AutoValue
@JsonSerialize(as = AccountUpdateRequest.class)
public abstract class AccountUpdateRequest {
  public static Builder builder() {
    return new AutoValue_AccountUpdateRequest.Builder();
  }

  @JsonProperty("name")
  @Nullable
  public abstract String name();

  @JsonProperty("email")
  @Nullable
  public abstract String email();

  @JsonProperty("username")
  @Nullable
  public abstract String username();

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setName(String name);

    public abstract Builder setEmail(String email);

    public abstract Builder setUsername(String username);

    public abstract AccountUpdateRequest build();
  }
}
