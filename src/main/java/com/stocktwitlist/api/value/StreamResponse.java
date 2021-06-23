package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.stocktwitlist.api.contract.Response;
import javax.annotation.Nullable;

/**
 * AutoValue of Streams API JSON response.
 *
 * <p>https://api.stocktwits.com/developers/docs/api#streams-user-docs
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_StreamResponse.Builder.class)
@JsonSerialize(as = StreamResponse.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class StreamResponse implements Response {

  public static Builder builder() {
    return new AutoValue_StreamResponse.Builder();
  }

  @JsonProperty("cursor")
  public abstract Cursor cursor();

  @JsonProperty("user")
  @Nullable
  public abstract User user();

  @JsonProperty("symbol")
  @Nullable
  public abstract Symbol symbol();

  @JsonProperty("watchlist")
  @Nullable
  public abstract Watchlist watchlist();

  @JsonProperty("messages")
  public abstract ImmutableList<Message> messages();

  /** Builder for Cursor AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("cursor")
    public abstract Builder setCursor(Cursor cursor);

    @JsonProperty("user")
    public abstract Builder setUser(User user);

    @JsonProperty("symbol")
    public abstract Builder setSymbol(Symbol symbol);

    @JsonProperty("messages")
    public abstract Builder setMessages(ImmutableList<Message> messages);

    @JsonProperty("watchlist")
    public abstract Builder setWatchlist(Watchlist watchlist);

    public abstract StreamResponse build();
  }
}
