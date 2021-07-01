package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.SearchRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.SearchResponse;

final class SearchRequestContext implements SearchRequest {
  private final Context context;

  SearchRequestContext(Context context) {
    this.context = context;
    context.appendPath("search").setResponseClass(SearchResponse.class);
  }

  @Override
  public Sendable<SearchResponse> index(String searchTerm) {
    return new GenericSendable<>(context.addQueryParam("q", searchTerm));
  }

  @Override
  public Sendable<SearchResponse> symbols(String searchTerm) {
    return new GenericSendable<>(context.appendPath("symbols").addQueryParam("q", searchTerm));
  }

  @Override
  public Sendable<SearchResponse> users(String searchTerm) {
    return new GenericSendable<>(context.appendPath("users").addQueryParam("q", searchTerm));
  }
}
