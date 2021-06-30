package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.contract.Response;

@AutoValue
@JsonDeserialize(builder = AutoValue_GraphResponse.Builder.class)
@JsonSerialize(as = GraphResponse.class)
public abstract class GraphResponse implements Response {
  public static Builder builder() {
    return new AutoValue_GraphResponse.Builder();
  }

  @JsonProperty("cursor")
  public abstract Cursor cursor();

  @JsonProperty("users")
  public abstract ImmutableList<User> users();

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("cursor")
    public abstract Builder setCursor(Cursor cursor);

    @JsonProperty("users")
    public abstract Builder setUsers(ImmutableList<User> users);

    public abstract GraphResponse build();
  }
}
