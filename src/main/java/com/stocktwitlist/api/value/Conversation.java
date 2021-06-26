package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;

/** AutoValue of Conversation JSON response. */
@AutoValue
@JsonDeserialize(builder = AutoValue_Conversation.Builder.class)
@JsonSerialize(as = Conversation.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Conversation {

  public static Builder builder() {
    return new AutoValue_Conversation.Builder();
  }

  @JsonProperty("parent_message_id")
  public abstract long parentMessageId();

  @JsonProperty("in_reply_to_message_id")
  public abstract long inReplyToMessageId();

  @JsonProperty("parent")
  public abstract boolean parent();

  @JsonProperty("replies")
  public abstract int replies();

  public abstract Builder toBuilder();

  /** Builder for Conversation AutoValue. */
  @AutoValue.Builder
  public abstract static class Builder {
    @JsonProperty("parent_message_id")
    public abstract Builder setParentMessageId(long id);

    @JsonProperty("in_reply_to_message_id")
    public abstract Builder setInReplyToMessageId(long id);

    @JsonProperty("parent")
    public abstract Builder setParent(boolean parent);

    @JsonProperty("replies")
    public abstract Builder setReplies(int replies);

    public abstract Conversation build();
  }
}
