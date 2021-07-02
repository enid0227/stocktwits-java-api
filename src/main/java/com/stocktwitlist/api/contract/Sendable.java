package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.exception.StocktwitsApiException;
import com.stocktwitlist.api.value.Response;

/** Represents a sendable request that returns a bounded response. */
public interface Sendable<T extends Response> {
  T send() throws StocktwitsApiException;
}
