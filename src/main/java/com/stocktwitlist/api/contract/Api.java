package com.stocktwitlist.api.contract;

import java.net.http.HttpClient;

/** Stocktwits API Client interface. */
public interface Api {
  Api setHttpClient(HttpClient httpClient);

  StreamRequest streams();

  SearchRequest search();

  MessageRequest messages();

  GraphRequest graph();

  FriendshipRequest friendships();

  WatchlistRequest watchlists();

  BlockRequest blocks();

  AccountRequest account();

  TrendingRequest trending();

  MuteRequest mutes();

  DeletionRequest deletions();
}
