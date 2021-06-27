package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/** AutoValue for message/create API params. */
@AutoValue
@JsonDeserialize(builder = AutoValue_CreateMessageRequest.Builder.class)
@JsonSerialize(as = CreateMessageRequest.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CreateMessageRequest {
  public static Builder builder() {
    return new AutoValue_CreateMessageRequest.Builder().setSentiment(Sentiment.NEUTRAL);
  }

  @JsonProperty("body")
  public abstract String body();

  @JsonProperty("in_reply_to_message_id")
  @Nullable
  public abstract Long inReplyToMessageId();

  /**
   * Chart with url format
   *
   * <p>Currently only support url format. Support for java.nio.Path may be supported later.
   */
  @JsonProperty("chart")
  @Nullable
  public abstract String fileUrl();

  @JsonProperty("sentiment")
  public final String getSentiment() {
    return sentiment().name().toLowerCase();
  }

  public abstract Sentiment sentiment();

  public enum Sentiment {
    BULLISH,
    BEARISH,
    NEUTRAL
  }

  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("body")
    public abstract Builder setBody(String body);

    @JsonProperty("in_reply_to_message_id")
    public abstract Builder setInReplyToMessageId(Long id);

    @JsonProperty("chart")
    public abstract Builder setFileUrl(String chart);

    public abstract Builder setSentiment(Sentiment sentiment);

    @JsonProperty("sentiment")
    public final Builder setSentiment(String sentiment) {
      for (Sentiment s : Sentiment.values()) {
        if (sentiment.equalsIgnoreCase(s.name())) {
          setSentiment(s);
          return this;
        }
      }
      throw new IllegalArgumentException("unknown sentiment");
    }

    public abstract CreateMessageRequest build();
  }
}
