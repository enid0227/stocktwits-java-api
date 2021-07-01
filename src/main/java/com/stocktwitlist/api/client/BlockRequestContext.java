package com.stocktwitlist.api.client;

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
    return new BlockSendable(
        context.setHttpMethod("POST").appendPath("create").appendPath("" + userId));
  }

  @Override
  public Sendable<BlockResponse> destroy(long userId) {
    return new BlockSendable(
        context.setHttpMethod("POST").appendPath("destroy").appendPath("" + userId));
  }

  static final class BlockSendable implements Sendable<BlockResponse> {
    private final Context context;

    BlockSendable(Context context) {
      this.context = context;
    }

    @Override
    public BlockResponse send() {
      String response = context.sendRequest();
      if (response == null) {
        return null;
      }

      return (BlockResponse) context.parseJsonString(response);
    }
  }
}
