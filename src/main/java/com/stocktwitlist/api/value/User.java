package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.time.LocalDate;
import javax.annotation.Nullable;

/** AutoValue of User JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_User.Builder.class)
@JsonSerialize(as = User.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class User {
  public static Builder builder() {
    return new AutoValue_User.Builder();
  }

  @JsonProperty("id")
  public abstract long id();

  @JsonProperty("username")
  public abstract String username();

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("avatar_url")
  public abstract String avatarUrl();

  @JsonProperty("avatar_url_ssl")
  public abstract String avatarUrlSsl();

  @JsonProperty("join_date")
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Nullable
  public abstract LocalDate joinDate();

  @JsonProperty("official")
  @Nullable
  public abstract Boolean official();

  @JsonProperty("identity")
  public abstract String identity();

  @JsonProperty("followers")
  @Nullable
  public abstract Integer followers();

  @JsonProperty("following")
  @Nullable
  public abstract Integer following();

  @JsonProperty("ideas")
  @Nullable
  public abstract Integer ideas();

  @JsonProperty("like_count")
  @Nullable
  public abstract Integer likeCount();

  @JsonProperty("plus_tier")
  @Nullable
  public abstract String plusTier();

  @JsonProperty("premium_room")
  @Nullable
  public abstract String premiumRoom();

  @JsonProperty("trade_app")
  @Nullable
  public abstract Boolean tradeApp();

  @JsonProperty("watchlist_stocks_count")
  @Nullable
  public abstract Integer watchListStocksCount();

  @JsonProperty("classification")
  @Nullable
  public abstract ImmutableList<String> classification();

  public abstract Builder toBuilder();

  /** Builder for User AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("id")
    public abstract Builder setId(long id);

    @JsonProperty("username")
    public abstract Builder setUsername(String username);

    @JsonProperty("name")
    public abstract Builder setName(String name);

    @JsonProperty("avatar_url")
    public abstract Builder setAvatarUrl(String avatarUrl);

    @JsonProperty("avatar_url_ssl")
    public abstract Builder setAvatarUrlSsl(String avatarUrlSsl);

    @JsonProperty("join_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    public abstract Builder setJoinDate(LocalDate joinDate);

    @JsonProperty("official")
    public abstract Builder setOfficial(boolean offical);

    @JsonProperty("identity")
    public abstract Builder setIdentity(String identity);

    @JsonProperty("followers")
    public abstract Builder setFollowers(int followers);

    @JsonProperty("following")
    public abstract Builder setFollowing(int following);

    @JsonProperty("ideas")
    public abstract Builder setIdeas(int ideas);

    @JsonProperty("like_count")
    public abstract Builder setLikeCount(int likeCount);

    @JsonProperty("plus_tier")
    public abstract Builder setPlusTier(String plusTier);

    @JsonProperty("premium_room")
    public abstract Builder setPremiumRoom(String premiumRoom);

    @JsonProperty("trade_app")
    public abstract Builder setTradeApp(boolean tradeApp);

    @JsonProperty("watchlist_stocks_count")
    public abstract Builder setWatchListStocksCount(int watchListStocksCount);

    @JsonProperty("classification")
    public abstract Builder setClassification(ImmutableList<String> classification);

    public abstract User build();
  }
}
