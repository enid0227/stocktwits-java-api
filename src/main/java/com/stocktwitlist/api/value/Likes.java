package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/** AutoValue of Likes JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Likes.Builder.class)
@JsonSerialize(as = Likes.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Likes {
  public static Builder builder() {
    return new AutoValue_Likes.Builder();
  }

  @JsonProperty("total")
  public abstract int total();

  @JsonProperty("user_ids")
  public abstract ImmutableList<Long> userIds();

  /** Builder for Likes AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("total")
    public abstract Builder setTotal(int total);

    @JsonProperty("user_ids")
    public abstract Builder setUserIds(ImmutableList<Long> userIds);

    public abstract Likes build();
  }
}
