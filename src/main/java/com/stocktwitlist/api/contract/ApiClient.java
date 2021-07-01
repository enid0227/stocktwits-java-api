package com.stocktwitlist.api.contract;

import java.net.http.HttpClient;

/** Stocktwits API Client interface. */
public interface ApiClient {
  ApiClient setHttpClient(HttpClient httpClient);

  StreamRequest streams();

  SearchRequest search();

  MessageRequest messages();

  GraphRequest graph();

  FriendshipRequest friendships();

  WatchlistRequest watchlists();

  BlockRequest blocks();
}
