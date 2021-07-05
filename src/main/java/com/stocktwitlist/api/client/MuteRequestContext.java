package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.MuteRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.MuteResponse;

public class MuteRequestContext implements MuteRequest {
  private final Context context;

  MuteRequestContext(Context context) {
    this.context = context.appendPath("mutes").setResponseClass(MuteResponse.class);
  }

  @Override
  public Sendable<MuteResponse> create(long userId) {
    return new GenericSendable<>(
        context.setHttpMethod("POST").appendPath("create").appendPath("" + userId));
  }

  @Override
  public Sendable<MuteResponse> destroy(long userId) {
    return new GenericSendable<>(
        context.setHttpMethod("POST").appendPath("destroy").appendPath("" + userId));
  }
}
