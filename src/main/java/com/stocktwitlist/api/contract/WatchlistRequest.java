package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.WatchlistResponse;
import java.util.List;

/** Collection of all watchlist API endpoints. */
public interface WatchlistRequest {
  Sendable<WatchlistResponse> index();

  /** Creates a new watchlist for the authenticated user */
  Sendable<WatchlistResponse> create(String name);

  /** Updates the name for the specific watchlist */
  Sendable<WatchlistResponse> update(long id, String name);

  /** Removes a specific watchlist */
  Sendable<WatchlistResponse> destroy(long id);

  /** Returns the the list of symbols in a specified watch list for the authenticating user */
  Sendable<WatchlistResponse> show(long id);

  /** Adds symbols to a specific watchlist */
  Sendable<WatchlistResponse> symbolsCreate(long id, List<String> symbols);

  /** Removes symbols from a specific watchlist */
  Sendable<WatchlistResponse> symbolsDestroy(long id, List<String> symbols);
}
