package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

/** AutoValue of Cursor JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Cursor.Builder.class)
@JsonSerialize(as = Cursor.class)
public abstract class Cursor {
  public static Builder builder() {
    return new AutoValue_Cursor.Builder();
  }

  @JsonProperty("more")
  public abstract boolean more();

  @JsonProperty("since")
  public abstract long since();

  @JsonProperty("max")
  public abstract long max();

  /** Builder for Cursor AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("more")
    public abstract Builder setMore(boolean more);

    @JsonProperty("since")
    public abstract Builder setSince(long since);

    @JsonProperty("max")
    public abstract Builder setMax(long max);

    public abstract Cursor build();
  }
}
