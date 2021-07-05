package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.MuteResponse;

/** Collection of all search API endpoints. */
public interface MuteRequest {
  Sendable<MuteResponse> create(long userId);

  Sendable<MuteResponse> destroy(long userId);
}
