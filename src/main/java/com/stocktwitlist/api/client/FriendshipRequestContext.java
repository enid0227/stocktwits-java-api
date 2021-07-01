package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.FriendshipRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.FriendshipResponse;

public class FriendshipRequestContext implements FriendshipRequest {
  private final Context context;

  FriendshipRequestContext(Context context) {
    this.context = context;
    this.context.appendPath("friendships").setResponseClass(FriendshipResponse.class);
  }

  @Override
  public Sendable<FriendshipResponse> create(long userId) {
    return new GenericSendable<>(
        context.appendPath("create").setHttpMethod("POST").appendPath("" + userId));
  }

  @Override
  public Sendable<FriendshipResponse> destroy(long userId) {
    return new GenericSendable<>(
        context.appendPath("destroy").setHttpMethod("POST").appendPath("" + userId));
  }
}
