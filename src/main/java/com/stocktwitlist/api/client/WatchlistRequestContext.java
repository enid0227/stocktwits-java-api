package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.contract.WatchlistRequest;
import com.stocktwitlist.api.value.WatchlistResponse;
import java.util.List;
import javax.annotation.Nullable;

public class WatchlistRequestContext implements WatchlistRequest {

  private final Context context;

  WatchlistRequestContext(Context context) {
    this.context = context.appendPath("watchlists").setResponseClass(WatchlistResponse.class);
  }

  @Override
  public Sendable<WatchlistResponse> index() {
    return new WatchlistSendable(context);
  }

  @Override
  public Sendable<WatchlistResponse> create(String name) {
    return new WatchlistSendable(
        context.setHttpMethod("POST").appendPath("create").addData("name", name));
  }

  @Override
  public Sendable<WatchlistResponse> update(long id, String name) {
    return new WatchlistSendable(
        context
            .appendPath("update")
            .appendPath("" + id)
            .setHttpMethod("POST")
            .addData("name", name));
  }

  @Override
  public Sendable<WatchlistResponse> destroy(long id) {
    return new WatchlistSendable(
        context.setHttpMethod("POST").appendPath("destroy").appendPath("" + id));
  }

  @Override
  public Sendable<WatchlistResponse> show(long id) {
    return new WatchlistSendable(context.appendPath("show").appendPath("" + id));
  }

  @Override
  public Sendable<WatchlistResponse> symbolsCreate(long id, List<String> symbols) {
    return new WatchlistSendable(
        context
            .setHttpMethod("POST")
            .appendPath("" + id)
            .appendPath("symbols")
            .appendPath("create")
            .addData("symbols", String.join(",", symbols)));
  }

  @Override
  public Sendable<WatchlistResponse> symbolsDestroy(long id, List<String> symbols) {
    return new WatchlistSendable(
        context
            .setHttpMethod("POST")
            .appendPath("" + id)
            .appendPath("symbols")
            .appendPath("destroy")
            .addData("symbols", String.join(",", symbols)));
  }

  static final class WatchlistSendable implements Sendable<WatchlistResponse> {
    private final Context context;

    WatchlistSendable(Context context) {
      this.context = context;
    }

    @Override
    @Nullable
    public WatchlistResponse send() {
      String response = context.sendRequest();
      if (response == null) {
        return null;
      }

      return (WatchlistResponse) context.parseJsonString(response);
    }
  }
}
