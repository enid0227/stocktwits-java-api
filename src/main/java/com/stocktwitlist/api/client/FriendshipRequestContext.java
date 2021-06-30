package com.stocktwitlist.api.client;

import com.stocktwitlist.api.contract.FriendshipRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.FriendshipResponse;
import javax.annotation.Nullable;

public class FriendshipRequestContext implements FriendshipRequest {
  private final Context context;

  FriendshipRequestContext(Context context) {
    this.context = context;
    this.context.appendPath("friendships").setResponseClass(FriendshipResponse.class);
  }

  @Override
  public Sendable<FriendshipResponse> create(long userId) {
    return new FriendshipSendable(
        context.appendPath("create").setHttpMethod("POST").appendPath("" + userId));
  }

  @Override
  public Sendable<FriendshipResponse> destroy(long userId) {
    return new FriendshipSendable(
        context.appendPath("destroy").setHttpMethod("POST").appendPath("" + userId));
  }

  static final class FriendshipSendable implements Sendable<FriendshipResponse> {
    private final Context context;

    FriendshipSendable(Context context) {
      this.context = context;
    }

    @Override
    @Nullable
    public FriendshipResponse send() {
      String response = context.sendRequest();
      if (response == null) {
        return null;
      }

      return (FriendshipResponse) context.parseJsonString(response);
    }
  }
}
