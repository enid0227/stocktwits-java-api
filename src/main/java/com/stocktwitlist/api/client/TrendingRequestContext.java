package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.contract.TrendingRequest;
import com.stocktwitlist.api.value.TrendingResponse;

public class TrendingRequestContext implements TrendingRequest {
  private final Context context;

  TrendingRequestContext(Context context) {
    this.context = context.appendPath("trending").setResponseClass(TrendingResponse.class);
  }

  @Override
  public Sendable<TrendingResponse> symbols() {
    return new GenericSendable<>(context.appendPath("symbols"));
  }

  @Override
  public Sendable<TrendingResponse> symbolsEquities() {
    return new GenericSendable<>(context.appendPath("symbols").appendPath("equities"));
  }
}
