package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;

@AutoValue
@JsonDeserialize(builder = AutoValue_WatchlistResponse.Builder.class)
@JsonSerialize(as = WatchlistResponse.class)
public abstract class WatchlistResponse implements Response {
  public static Builder builder() {
    return new AutoValue_WatchlistResponse.Builder();
  }

  @JsonProperty("watchlists")
  @Nullable
  /** Property for watchlists.json endpoint */
  public abstract ImmutableList<Watchlist> watchlists();

  /** Property for watchlists/ CRUD endpoints */
  @JsonProperty("watchlist")
  @Nullable
  public abstract Watchlist watchlist();

  /** Property for watchlist/:id/symbols/ endpoints */
  @JsonProperty("symbols")
  @Nullable
  public abstract ImmutableList<Symbol> symbols();

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("watchlists")
    public abstract Builder setWatchlists(ImmutableList<Watchlist> watchlists);

    @JsonProperty("watchlist")
    public abstract Builder setWatchlist(Watchlist watchlist);

    @JsonProperty("symbols")
    public abstract Builder setSymbols(ImmutableList<Symbol> symbols);

    public abstract WatchlistResponse build();
  }
}
