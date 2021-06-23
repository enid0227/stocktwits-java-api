package com.stocktwitlist.api.contract;

/** Represents a sendable request that returns a bounded response. */
public interface Sendable<T extends Response> {
  T send();
}
