package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.contract.StreamRequest;
import com.stocktwitlist.api.value.StreamResponse;
import java.util.List;

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
    return this;
  }

  @Override
  public StreamRequest setLimit(int limit) {
    context.setLimit(limit);
    return this;
  }

  @Override
  public Sendable<StreamResponse> user(long userId) {
    return new GenericSendable<>(context.appendPath("user").appendPath("" + userId));
  }

  @Override
  public Sendable<StreamResponse> user(String username) {
    return new GenericSendable<>(context.appendPath("user").appendPath(username));
  }

  @Override
  public Sendable<StreamResponse> symbol(long symbolId) {
    return new GenericSendable<>(context.appendPath("symbol").appendPath("" + symbolId));
  }

  @Override
  public Sendable<StreamResponse> symbol(String symbol) {
    return new GenericSendable<>(context.appendPath("symbol").appendPath(symbol));
  }

  @Override
  public Sendable<StreamResponse> friends() {
    return new GenericSendable<>(context.appendPath("friends"));
  }

  @Override
  public Sendable<StreamResponse> mentions() {
    return new GenericSendable<>(context.appendPath("mentions"));
  }

  @Override
  public Sendable<StreamResponse> watchlist() {
    return new GenericSendable<>(context.appendPath("watchlist"));
  }

  @Override
  public Sendable<StreamResponse> direct() {
    return new GenericSendable<>(context.appendPath("direct"));
  }

  @Override
  public Sendable<StreamResponse> directSent() {
    return new GenericSendable<>(context.appendPath("direct_sent"));
  }

  @Override
  public Sendable<StreamResponse> directReceived() {
    return new GenericSendable<>(context.appendPath("direct_received"));
  }

  @Override
  public Sendable<StreamResponse> all() {
    return new GenericSendable<>(context.appendPath("all"));
  }

  @Override
  public Sendable<StreamResponse> charts() {
    return new GenericSendable<>(context.appendPath("charts"));
  }

  @Override
  public Sendable<StreamResponse> equities() {
    return new GenericSendable<>(context.appendPath("equities"));
  }

  @Override
  public Sendable<StreamResponse> forex() {
    return new GenericSendable<>(context.appendPath("forex"));
  }

  @Override
  public Sendable<StreamResponse> futures() {
    return new GenericSendable<>(context.appendPath("futures"));
  }

  @Override
  public Sendable<StreamResponse> private_companies() {
    return new GenericSendable<>(context.appendPath("private_companies"));
  }

  @Override
  public Sendable<StreamResponse> suggested() {
    return new GenericSendable<>(context.appendPath("suggested"));
  }

  @Override
  public Sendable<StreamResponse> symbols(List<String> symbols) {
    if (symbols.size() > 10) {
      throw new IllegalArgumentException("exceeded max allowed 10 symbols.");
    }
    return new GenericSendable<>(
        context.appendPath("symbols").addQueryParam("symbols", String.join(",", symbols)));
  }

  @Override
  public Sendable<StreamResponse> trending() {
    return new GenericSendable<>(context.appendPath("trending"));
  }

  @Override
  public Sendable<StreamResponse> sectors(String sector) {
    return new GenericSendable<>(context.appendPath("sectors").appendPath(sector));
  }

  @Override
  public Sendable<StreamResponse> conversation(long conversationId) {
    return new GenericSendable<>(
        context.appendPath("conversation").appendPath("" + conversationId));
  }
}
