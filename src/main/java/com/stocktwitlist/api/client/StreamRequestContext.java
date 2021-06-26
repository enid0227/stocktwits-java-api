package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.contract.StreamRequest;
import com.stocktwitlist.api.value.StreamResponse;
import java.util.List;
import javax.annotation.Nullable;

final class StreamRequestContext implements StreamRequest {
  private final Context context;

  StreamRequestContext(Context context) {
    this.context = context;
    context.appendPath("streams").setResponseClass(StreamResponse.class);
  }

  @Override
  public StreamRequest setSince(long since) {
    context.setSince(since);
    return this;
  }

  @Override
  public StreamRequest setMax(long max) {
    context.setMax(max);
    return null;
  }

  @Override
  public StreamRequest setLimit(int limit) {
    context.setLimit(limit);
    return this;
  }

  @Override
  public Sendable<StreamResponse> user(long userId) {
    return new StreamSendable(context.appendPath("user").appendPath("" + userId));
  }

  @Override
  public Sendable<StreamResponse> user(String username) {
    return new StreamSendable(context.appendPath("user").appendPath(username));
  }

  @Override
  public Sendable<StreamResponse> symbol(long symbolId) {
    return new StreamSendable(context.appendPath("symbol").appendPath("" + symbolId));
  }

  @Override
  public Sendable<StreamResponse> symbol(String symbol) {
    return new StreamSendable(context.appendPath("symbol").appendPath(symbol));
  }

  @Override
  public Sendable<StreamResponse> friends() {
    return new StreamSendable(context.appendPath("friends"));
  }

  @Override
  public Sendable<StreamResponse> mentions() {
    return new StreamSendable(context.appendPath("mentions"));
  }

  @Override
  public Sendable<StreamResponse> watchlist() {
    return new StreamSendable(context.appendPath("watchlist"));
  }

  @Override
  public Sendable<StreamResponse> direct() {
    return new StreamSendable(context.appendPath("direct"));
  }

  @Override
  public Sendable<StreamResponse> directSent() {
    return new StreamSendable(context.appendPath("direct_sent"));
  }

  @Override
  public Sendable<StreamResponse> directReceived() {
    return new StreamSendable(context.appendPath("direct_received"));
  }

  @Override
  public Sendable<StreamResponse> all() {
    return new StreamSendable(context.appendPath("all"));
  }

  @Override
  public Sendable<StreamResponse> charts() {
    return new StreamSendable(context.appendPath("charts"));
  }

  @Override
  public Sendable<StreamResponse> equities() {
    return new StreamSendable(context.appendPath("equities"));
  }

  @Override
  public Sendable<StreamResponse> forex() {
    return new StreamSendable(context.appendPath("forex"));
  }

  @Override
  public Sendable<StreamResponse> futures() {
    return new StreamSendable(context.appendPath("futures"));
  }

  @Override
  public Sendable<StreamResponse> private_companies() {
    return new StreamSendable(context.appendPath("private_companies"));
  }

  @Override
  public Sendable<StreamResponse> suggested() {
    return new StreamSendable(context.appendPath("suggested"));
  }

  @Override
  public Sendable<StreamResponse> symbols(List<String> symbols) {
    if (symbols.size() > 10) {
      throw new IllegalArgumentException("exceeded max allowed 10 symbols.");
    }
    return new StreamSendable(
        context.appendPath("symbols").addData("symbols", String.join(",", symbols)));
  }

  @Override
  public Sendable<StreamResponse> trending() {
    return new StreamSendable(context.appendPath("trending"));
  }

  @Override
  public Sendable<StreamResponse> sectors(String sector) {
    return new StreamSendable(context.appendPath("sectors").appendPath(sector));
  }

  @Override
  public Sendable<StreamResponse> conversation(long conversationId) {
    return new StreamSendable(context.appendPath("conversation").appendPath("" + conversationId));
  }

  static final class StreamSendable implements Sendable<StreamResponse> {
    private final Context context;

    StreamSendable(Context context) {
      this.context = context;
    }

    @Override
    @Nullable
    public StreamResponse send() {
      String response = context.sendRequest();
      if (response == null) {
        return null;
      }
      return (StreamResponse) context.parseJsonString(response);
    }
  }
}
