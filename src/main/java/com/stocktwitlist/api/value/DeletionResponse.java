package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;

@AutoValue
@JsonDeserialize(builder = AutoValue_DeletionResponse.Builder.class)
@JsonSerialize(as = DeletionResponse.class)
public abstract class DeletionResponse implements Response {
  public static Builder builder() {
    return new AutoValue_DeletionResponse.Builder();
  }

  @JsonProperty("cursor")
  @Nullable
  public abstract Cursor cursor();

  @JsonProperty("users")
  @Nullable
  public abstract ImmutableList<User> users();

  @JsonProperty("messages")
  @Nullable
  public abstract ImmutableList<Message> messages();

  public abstract DeletionResponse.Builder toBuilder();

  /** Builder for DeletionResponse AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("cursor")
    public abstract DeletionResponse.Builder setCursor(Cursor cursor);

    @JsonProperty("users")
    public abstract DeletionResponse.Builder setUsers(ImmutableList<User> users);

    @JsonProperty("messages")
    public abstract DeletionResponse.Builder setMessages(ImmutableList<Message> messages);

    public abstract DeletionResponse build();
  }
}
