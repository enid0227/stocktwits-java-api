package com.stocktwitlist.api.contract;

/** Allows the request pagination configuration. */
public interface Pageable<T> {
  T setSince(long since);

  T setMax(long max);

  T setLimit(int limit);
}
