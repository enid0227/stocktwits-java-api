package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.BlockRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.BlockResponse;

public class BlockRequestContext implements BlockRequest {
  private final Context context;

  BlockRequestContext(Context context) {
    this.context = context.appendPath("blocks").setResponseClass(BlockResponse.class);
  }

  @Override
  public Sendable<BlockResponse> create(long userId) {
    return new GenericSendable<>(
        context.setHttpMethod("POST").appendPath("create").appendPath("" + userId));
  }

  @Override
  public Sendable<BlockResponse> destroy(long userId) {
    return new GenericSendable<>(
        context.setHttpMethod("POST").appendPath("destroy").appendPath("" + userId));
  }
}
