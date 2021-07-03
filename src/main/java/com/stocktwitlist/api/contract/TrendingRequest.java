package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.TrendingResponse;
/** Collection of all trending API endpoints. */
public interface TrendingRequest {

  /** Returns a list of all the trending symbols at the moment requested. */
  Sendable<TrendingResponse> symbols();

  /** Returns a list of all the trending equity symbols at the moment requested. */
  Sendable<TrendingResponse> symbolsEquities();
}
