package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

/** AutoValue of Source JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Source.Builder.class)
@JsonSerialize(as = Source.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Source {
  public static Builder builder() {
    return new AutoValue_Source.Builder();
  }

  @JsonProperty("id")
  public abstract long id();

  @JsonProperty("title")
  public abstract String title();

  @JsonProperty("url")
  public abstract String url();

  /** Builder for Source AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("id")
    public abstract Builder setId(long id);

    @JsonProperty("title")
    public abstract Builder setTitle(String title);

    @JsonProperty("url")
    public abstract Builder setUrl(String url);

    public abstract Source build();
  }
}
