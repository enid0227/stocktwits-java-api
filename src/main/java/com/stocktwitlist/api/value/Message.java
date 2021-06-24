package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.time.Instant;
import javax.annotation.Nullable;

/** AutoValue of Message JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Message.Builder.class)
@JsonSerialize(as = Message.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Message {

  public static Builder builder() {
    return new AutoValue_Message.Builder();
  }

  @JsonProperty("id")
  public abstract long id();

  @JsonProperty("body")
  public abstract String body();

  @JsonProperty("created_at")
  public abstract Instant createdAt();

  @JsonProperty("source")
  public abstract Source source();

  @JsonProperty("symbols")
  public abstract ImmutableList<Symbol> symbols();

  @JsonProperty("user")
  public abstract User user();

  @JsonProperty("mentioned_users")
  public abstract ImmutableList<String> mentionedUsers();

  @JsonProperty("liked_by_self")
  @Nullable
  public abstract Boolean likedBySelf();

  @JsonProperty("reshared_by_self")
  @Nullable
  public abstract Boolean resharedBySelf();

  @JsonProperty("conversation")
  @Nullable
  public abstract Conversation conversation();

  @JsonProperty("likes")
  @Nullable
  public abstract Likes likes();

  // TODO: entities
  // - "sentiment": {"basic": "Bullish"}
  // - "giphy": {"id": "<giphy-id>", "ratio": 1.41421}

  /** Builder for Message AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {

    @JsonProperty("id")
    public abstract Builder setId(long id);

    @JsonProperty("body")
    public abstract Builder setBody(String body);

    @JsonProperty("created_at")
    public abstract Builder setCreatedAt(Instant createdAt);

    @JsonProperty("source")
    public abstract Builder setSource(Source source);

    @JsonProperty("symbols")
    public abstract Builder setSymbols(ImmutableList<Symbol> symbols);

    @JsonProperty("user")
    public abstract Builder setUser(User user);

    @JsonProperty("mentioned_users")
    public abstract Builder setMentionedUsers(ImmutableList<String> mentionedUsers);

    @JsonProperty("liked_by_self")
    public abstract Builder setLikedBySelf(Boolean likedBySelf);

    @JsonProperty("reshared_by_self")
    public abstract Builder setResharedBySelf(Boolean resharedBySelf);

    @JsonProperty("conversation")
    public abstract Builder setConversation(Conversation conversation);

    @JsonProperty("likes")
    public abstract Builder setLikes(Likes likes);

    public abstract Message build();
  }
}
