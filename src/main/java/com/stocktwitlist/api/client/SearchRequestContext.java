package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.SearchRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.SearchResponse;
import javax.annotation.Nullable;

final class SearchRequestContext implements SearchRequest {
  private final Context context;

  SearchRequestContext(Context context) {
    this.context = context;
    context.appendPath("search").setResponseClass(SearchResponse.class);
  }

  @Override
  public Sendable<SearchResponse> index(String searchTerm) {
    return new SearchSendable(context.addQueryParam("q", searchTerm));
  }

  @Override
  public Sendable<SearchResponse> symbols(String searchTerm) {
    return new SearchSendable(context.appendPath("symbols").addQueryParam("q", searchTerm));
  }

  @Override
  public Sendable<SearchResponse> users(String searchTerm) {
    return new SearchSendable(context.appendPath("users").addQueryParam("q", searchTerm));
  }

  static final class SearchSendable implements Sendable<SearchResponse> {
    private final Context context;

    SearchSendable(Context context) {
      this.context = context;
    }

    @Override
    @Nullable
    public SearchResponse send() {
      String response = context.sendRequest();
      if (response == null) {
        return null;
      }
      return (SearchResponse) context.parseJsonString(response);
    }
  }
}
