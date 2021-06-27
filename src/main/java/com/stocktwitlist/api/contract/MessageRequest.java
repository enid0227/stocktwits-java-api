package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.CreateMessageRequest;
import com.stocktwitlist.api.value.MessageResponse;

/** Collection of all messages API endpoints. */
public interface MessageRequest {
  Sendable<MessageResponse> create(CreateMessageRequest request);

  Sendable<MessageResponse> show(long messageId);

  Sendable<MessageResponse> like(long messageId);

  Sendable<MessageResponse> unlike(long messageId);
}
