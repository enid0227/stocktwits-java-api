package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.GraphRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.GraphResponse;

public class GraphRequestContext implements GraphRequest {
  private final Context context;

  GraphRequestContext(Context context) {
    this.context = context;
    context.appendPath("graph").setResponseClass(GraphResponse.class);
  }

  @Override
  public GraphRequest setSince(long since) {
    context.setSince(since);
    return this;
  }

  @Override
  public GraphRequest setMax(long max) {
    context.setMax(max);
    return null;
  }

  @Override
  public GraphRequest setLimit(int limit) {
    context.setLimit(limit);
    return this;
  }

  @Override
  public Sendable<GraphResponse> blocking() {
    return new GenericSendable<>(context.appendPath("blocking"));
  }

  @Override
  public Sendable<GraphResponse> muting() {
    return new GenericSendable<>(context.appendPath("muting"));
  }

  @Override
  public Sendable<GraphResponse> following() {
    return new GenericSendable<>(context.appendPath("following"));
  }

  @Override
  public Sendable<GraphResponse> followers() {
    return new GenericSendable<>(context.appendPath("followers"));
  }

  @Override
  public Sendable<GraphResponse> symbols() {
    return new GenericSendable<>(context.appendPath("symbols"));
  }
}
