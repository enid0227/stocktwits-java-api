package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.ApiClient;
import com.stocktwitlist.api.contract.StreamRequest;

/** Basic implementation for stocktwits.com API */
public final class StocktwitsApiClient implements ApiClient {
  private final Context context;

  private StocktwitsApiClient(String accessToken) {
    this.context = new Context(accessToken);
  }

  /**
   * Returns a new api client.
   *
   * @param accessToken for the authenticated user
   * @return a client with endpoint api methods
   */
  public static ApiClient newRequest(String accessToken) {
    return new StocktwitsApiClient(accessToken);
  }

  @Override
  public StreamRequest streams() {
    return new StreamRequestContext(context);
  }
}