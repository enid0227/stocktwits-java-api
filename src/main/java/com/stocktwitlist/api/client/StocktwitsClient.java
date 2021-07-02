package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.Api;

/** Stocktwits API Client */
public class StocktwitsClient {
  public StocktwitsClient() {}

  /**
   * Returns a new Api with a new Context.
   *
   * <p>Each returned Api instance has its own request context.
   *
   * @param accessToken for the authenticated user
   * @return an Api with all endpoint methods
   */
  public Api newRequest(String accessToken) {
    return new StocktwitsApi(accessToken);
  }
}
