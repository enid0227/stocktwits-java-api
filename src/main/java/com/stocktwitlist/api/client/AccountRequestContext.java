package com.stocktwitlist.api.client;

import com.stocktwitlist.api.client.Context.GenericSendable;
import com.stocktwitlist.api.contract.AccountRequest;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.value.AccountResponse;
import com.stocktwitlist.api.value.AccountUpdateRequest;

public class AccountRequestContext implements AccountRequest {
  private final Context context;

  AccountRequestContext(Context context) {
    this.context = context.appendPath("account").setResponseClass(AccountResponse.class);
  }

  @Override
  public Sendable<AccountResponse> verify() {
    return new GenericSendable<>(context.appendPath("verify"));
  }

  @Override
  public Sendable<AccountResponse> update(AccountUpdateRequest updateRequest) {
    if (updateRequest == null) {
      throw new IllegalArgumentException("updateRequest cannot be null");
    }
    if (updateRequest.email() != null) {
      context.addData("email", updateRequest.email());
    }
    if (updateRequest.name() != null) {
      context.addData("name", updateRequest.name());
    }
    if (updateRequest.username() != null) {
      context.addData("username", updateRequest.username());
    }
    return new GenericSendable<>(context.setHttpMethod("POST").appendPath("verify"));
  }
}
