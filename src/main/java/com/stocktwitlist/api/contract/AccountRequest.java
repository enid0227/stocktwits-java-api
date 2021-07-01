package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.AccountResponse;
import com.stocktwitlist.api.value.AccountUpdateRequest;

/** Collection of all account API endpoints. */
public interface AccountRequest {
  Sendable<AccountResponse> verify();

  Sendable<AccountResponse> update(AccountUpdateRequest updateRequest);
}
