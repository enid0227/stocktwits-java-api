package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.FriendshipResponse;

/** Collection of all graph API endpoints. */
public interface FriendshipRequest {

  /** Follows a user and create a friendship and receive all messages from this user. */
  Sendable<FriendshipResponse> create(long userId);

  /** Unfollows a user and end the friendship and no longer receive the users messages. */
  Sendable<FriendshipResponse> destroy(long userId);
}
