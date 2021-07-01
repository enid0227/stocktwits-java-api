package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.contract.WatchlistRequest;
import com.stocktwitlist.api.value.WatchlistResponse;
import java.util.List;

public class WatchlistRequestContext implements WatchlistRequest {

  private final Context context;

  WatchlistRequestContext(Context context) {
    this.context = context.appendPath("watchlists").setResponseClass(WatchlistResponse.class);
  }

  @Override
  public Sendable<WatchlistResponse> index() {
    return new GenericSendable<>(context);
  }

  @Override
  public Sendable<WatchlistResponse> create(String name) {
    return new GenericSendable<>(
        context.setHttpMethod("POST").appendPath("create").addData("name", name));
  }

  @Override
  public Sendable<WatchlistResponse> update(long id, String name) {
    return new GenericSendable<>(
        context
            .appendPath("update")
            .appendPath("" + id)
            .setHttpMethod("POST")
            .addData("name", name));
  }

  @Override
  public Sendable<WatchlistResponse> destroy(long id) {
    return new GenericSendable<>(
        context.setHttpMethod("POST").appendPath("destroy").appendPath("" + id));
  }

  @Override
  public Sendable<WatchlistResponse> show(long id) {
    return new GenericSendable<>(context.appendPath("show").appendPath("" + id));
  }

  @Override
  public Sendable<WatchlistResponse> symbolsCreate(long id, List<String> symbols) {
    return new GenericSendable<>(
        context
            .setHttpMethod("POST")
            .appendPath("" + id)
            .appendPath("symbols")
            .appendPath("create")
            .addData("symbols", String.join(",", symbols)));
  }

  @Override
  public Sendable<WatchlistResponse> symbolsDestroy(long id, List<String> symbols) {
    return new GenericSendable<>(
        context
            .setHttpMethod("POST")
            .appendPath("" + id)
            .appendPath("symbols")
            .appendPath("destroy")
            .addData("symbols", String.join(",", symbols)));
  }
}
