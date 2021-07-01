package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.ApiClient;
import com.stocktwitlist.api.contract.FriendshipRequest;
import com.stocktwitlist.api.contract.GraphRequest;
import com.stocktwitlist.api.contract.MessageRequest;
import com.stocktwitlist.api.contract.SearchRequest;
import com.stocktwitlist.api.contract.StreamRequest;
import com.stocktwitlist.api.contract.WatchlistRequest;
import java.net.http.HttpClient;

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
  public ApiClient setHttpClient(HttpClient httpClient) {
    context.setHttpClient(httpClient);
    return this;
  }

  @Override
  public StreamRequest streams() {
    return new StreamRequestContext(context);
  }

  @Override
  public SearchRequest search() {
    return new SearchRequestContext(context);
  }

  @Override
  public MessageRequest messages() {
    return new MessageRequestContext(context);
  }

  @Override
  public GraphRequest graph() {
    return new GraphRequestContext(context);
  }

  @Override
  public FriendshipRequest friendships() {
    return new FriendshipRequestContext(context);
  }

  @Override
  public WatchlistRequest watchlists() {
    return new WatchlistRequestContext(context);
  }
}
