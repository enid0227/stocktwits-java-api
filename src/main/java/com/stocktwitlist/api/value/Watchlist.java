package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.time.Instant;
import javax.annotation.Nullable;

/** AutoValue for Watchlist JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Watchlist.Builder.class)
@JsonSerialize(as = Watchlist.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Watchlist {
  public static Builder builder() {
    return new AutoValue_Watchlist.Builder();
  }

  @JsonProperty("id")
  public abstract long id();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("updated_at")
  public abstract Instant updatedAt();

  @JsonProperty("created_at")
  public abstract Instant createdAt();

  @JsonProperty("symbols")
  @Nullable
  public abstract ImmutableList<Symbol> symbols();

  /** Builder for Watchlist AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("id")
    public abstract Builder setId(long id);

    @JsonProperty("name")
    public abstract Builder setName(String name);

    @JsonProperty("updated_at")
    public abstract Builder setUpdatedAt(Instant updateAt);

    @JsonProperty("created_at")
    public abstract Builder setCreatedAt(Instant createAt);

    @JsonProperty("symbols")
    public abstract Builder setSymbols(ImmutableList<Symbol> symbols);

    public abstract Watchlist build();
  }
}
