package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;

/**
 * AutoValue for SearchResult that represents either a Symbol or an User.
 *
 * <p>While in theory, this can be done with jackson inheritance, a dedicate value class seems to be
 * a simpler solution to this problem. See {@link com.fasterxml.jackson.annotation.JsonTypeInfo}.
 * Jackson inheritance may introduce potential unwanted interdependency between regular User and
 * regular Symbol value classes.
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_SearchResult.Builder.class)
@JsonSerialize(as = SearchResult.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SearchResult {
  public static Builder builder() {
    return new AutoValue_SearchResult.Builder();
  }

  @JsonProperty("id")
  public abstract long id();

  @JsonProperty("type")
  public abstract String type();

  @JsonProperty("symbol")
  @Nullable
  public abstract String symbol();

  @JsonProperty("title")
  @Nullable
  public abstract String title();

  @JsonProperty("username")
  @Nullable
  public abstract String username();

  @JsonProperty("name")
  @Nullable
  public abstract String name();

  @JsonProperty("avatar_url")
  @Nullable
  public abstract String avatarUrl();

  @JsonProperty("avatar_url_ssl")
  @Nullable
  public abstract String avatarUrlSsl();

  @JsonProperty("identity")
  @Nullable
  public abstract String identity();

  @JsonProperty("classification")
  @Nullable
  public abstract ImmutableList<String> classification();

  /** Builder for SearchResult AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("id")
    public abstract Builder setId(long id);

    @JsonProperty("type")
    public abstract Builder setType(String type);

    @JsonProperty("symbol")
    public abstract Builder setSymbol(String symbol);

    @JsonProperty("title")
    public abstract Builder setTitle(String title);

    @JsonProperty("username")
    public abstract Builder setUsername(String username);

    @JsonProperty("name")
    public abstract Builder setName(String name);

    @JsonProperty("avatar_url")
    public abstract Builder setAvatarUrl(String avatarUrl);

    @JsonProperty("avatar_url_ssl")
    public abstract Builder setAvatarUrlSsl(String avatarUrlSsl);

    @JsonProperty("identity")
    public abstract Builder setIdentity(String identity);

    @JsonProperty("classification")
    public abstract Builder setClassification(ImmutableList<String> classification);

    public abstract SearchResult build();
  }
}
