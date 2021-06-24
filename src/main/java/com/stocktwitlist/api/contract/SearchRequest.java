package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.SearchResponse;

/** Collection of all search API endpoints. */
public interface SearchRequest {
  /** Search for a symbol or an user. 30 Results will be a combined list of symbols and users. */
  Sendable<SearchResponse> index(String searchTerm);

  /** Search for a symbol. 30 Results will be a combined list of symbols and users. */
  Sendable<SearchResponse> symbols(String searchTerm);

  /** search for an user. 30 Results will be a combined list of symbols and users. */
  Sendable<SearchResponse> users(String searchTerm);
}
