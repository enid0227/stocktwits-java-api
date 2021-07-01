package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.BlockResponse;

/** Collection of all blocks API endpoints. */
public interface BlockRequest {
  Sendable<BlockResponse> create(long userId);

  Sendable<BlockResponse> destroy(long userId);
}
