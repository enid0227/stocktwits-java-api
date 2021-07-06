package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.DeletionRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.DeletionResponse;

final class DeletionRequestContext implements DeletionRequest {
  private final Context context;

  DeletionRequestContext(Context context) {
    this.context = context;
    context.appendPath("deletions").setResponseClass(DeletionResponse.class);
  }

  @Override
  public Sendable<DeletionResponse> users() {
    return new GenericSendable<>(context.appendPath("users"));
  }

  @Override
  public Sendable<DeletionResponse> messages() {
    return new GenericSendable<>(context.appendPath("messages"));
  }

  @Override
  public DeletionRequest setSince(long since) {
    context.setSince(since);
    return this;
  }

  @Override
  public DeletionRequest setMax(long max) {
    context.setMax(max);
    return this;
  }

  @Override
  public DeletionRequest setLimit(int limit) {
    context.setLimit(limit);
    return this;
  }
}
