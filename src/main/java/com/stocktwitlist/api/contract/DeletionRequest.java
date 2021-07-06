package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.DeletionResponse;

/** Collection of all DeletionRequest API endpoints. */
public interface DeletionRequest extends Pageable<DeletionRequest> {
  Sendable<DeletionResponse> users();

  Sendable<DeletionResponse> messages();
}
