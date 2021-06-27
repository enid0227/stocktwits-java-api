package com.stocktwitlist.api.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.auto.value.AutoValue;
import com.stocktwitlist.api.contract.Response;

/** AutoValue of Messages API json response */
@AutoValue
@JsonSerialize(as = MessageResponse.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MessageResponse implements Response {
  @JsonCreator
  public static MessageResponse create(@JsonProperty("message") Message message) {
    return new AutoValue_MessageResponse(message);
  }

  @JsonProperty("message")
  public abstract Message message();
}
