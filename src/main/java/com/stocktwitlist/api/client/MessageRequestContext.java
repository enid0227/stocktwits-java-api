package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.MessageRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.CreateMessageRequest;
import com.stocktwitlist.api.value.MessageResponse;

public class MessageRequestContext implements MessageRequest {

  private final Context context;

  MessageRequestContext(Context context) {
    this.context = context;
    context.appendPath("messages").setResponseClass(MessageResponse.class);
  }

  @Override
  public Sendable<MessageResponse> create(CreateMessageRequest request) {
    if (request.inReplyToMessageId() != null) {
      context.addData("in_reply_to_message_id", "" + request.inReplyToMessageId());
    }
    if (request.fileUrl() != null) {
      context.addData("chart", request.fileUrl());
    }
    return new GenericSendable<>(
        context
            .appendPath("create")
            .setHttpMethod("POST")
            .addData("body", "" + request.body())
            .addData("sentiment", request.getSentiment()));
  }

  @Override
  public Sendable<MessageResponse> show(long messageId) {
    return new GenericSendable<>(context.appendPath("show").appendPath("" + messageId));
  }

  @Override
  public Sendable<MessageResponse> like(long messageId) {
    return new GenericSendable<>(
        context.appendPath("like").setHttpMethod("POST").addData("id", "" + messageId));
  }

  @Override
  public Sendable<MessageResponse> unlike(long messageId) {
    return new GenericSendable<>(
        context.appendPath("unlike").setHttpMethod("POST").addData("id", "" + messageId));
  }
}
