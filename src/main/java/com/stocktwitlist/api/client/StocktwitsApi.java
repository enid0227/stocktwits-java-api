package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.AccountRequest;
import com.stocktwitlist.api.contract.Api;
import com.stocktwitlist.api.contract.BlockRequest;
import com.stocktwitlist.api.contract.FriendshipRequest;
import com.stocktwitlist.api.contract.GraphRequest;
import com.stocktwitlist.api.contract.MessageRequest;
import com.stocktwitlist.api.contract.MuteRequest;
import com.stocktwitlist.api.contract.SearchRequest;
import com.stocktwitlist.api.contract.StreamRequest;
import com.stocktwitlist.api.contract.TrendingRequest;
import com.stocktwitlist.api.contract.WatchlistRequest;
import java.net.http.HttpClient;

/** Basic implementation for stocktwits.com API */
public final class StocktwitsApi implements Api {
  private final Context context;

  StocktwitsApi(String accessToken) {
    this.context = new Context(accessToken);
  }

  @Override
  public Api setHttpClient(HttpClient httpClient) {
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

  @Override
  public BlockRequest blocks() {
    return new BlockRequestContext(context);
  }

  @Override
  public AccountRequest account() {
    return new AccountRequestContext(context);
  }

  @Override
  public TrendingRequest trending() {
    return new TrendingRequestContext(context);
  }

  @Override
  public MuteRequest mutes() {
    return new MuteRequestContext(context);
  }
}
