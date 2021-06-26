package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/** AutoValue of Symbol JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Symbol.Builder.class)
@JsonSerialize(as = Symbol.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Symbol {

  public static Builder builder() {
    return new AutoValue_Symbol.Builder();
  }

  @JsonProperty("id")
  public abstract long id();

  @JsonProperty("symbol")
  public abstract String symbol();

  @JsonProperty("title")
  public abstract String title();

  @JsonProperty("is_following")
  @Nullable
  public abstract Boolean isFollowing();

  @JsonProperty("watchlist_count")
  @Nullable
  public abstract Integer watchlistCount();

  @JsonProperty("has_pricing")
  @Nullable
  public abstract Boolean hasPricing();

  public abstract Builder toBuilder();

  /** Builder for Symbol AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {

    @JsonProperty("id")
    public abstract Builder setId(long id);

    @JsonProperty("symbol")
    public abstract Builder setSymbol(String symbol);

    @JsonProperty("title")
    public abstract Builder setTitle(String title);

    @JsonProperty("is_following")
    public abstract Builder setIsFollowing(Boolean isFollowing);

    @JsonProperty("watchlist_count")
    public abstract Builder setWatchlistCount(Integer count);

    @JsonProperty("has_pricing")
    public abstract Builder setHasPricing(Boolean hasPricing);

    public abstract Symbol build();
  }
}
