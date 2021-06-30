package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.GraphRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.GraphResponse;
import javax.annotation.Nullable;

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
    return new GraphSendable(context.appendPath("blocking"));
  }

  @Override
  public Sendable<GraphResponse> muting() {
    return new GraphSendable(context.appendPath("muting"));
  }

  @Override
  public Sendable<GraphResponse> following() {
    return new GraphSendable(context.appendPath("following"));
  }

  @Override
  public Sendable<GraphResponse> followers() {
    return new GraphSendable(context.appendPath("followers"));
  }

  @Override
  public Sendable<GraphResponse> symbols() {
    return new GraphSendable(context.appendPath("symbols"));
  }

  static final class GraphSendable implements Sendable<GraphResponse> {
    private final Context context;

    GraphSendable(Context context) {
      this.context = context;
    }

    @Override
    @Nullable
    public GraphResponse send() {
      String response = context.sendRequest();
      if (response == null) {
        return null;
      }
      return (GraphResponse) context.parseJsonString(response);
    }
  }
}
