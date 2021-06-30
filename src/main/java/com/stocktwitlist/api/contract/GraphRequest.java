package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.GraphResponse;

/** Collection of all graph API endpoints. */
public interface GraphRequest extends Pageable<GraphRequest> {
  /** Returns the list of users that were blocked by the authenticating user. */
  Sendable<GraphResponse> blocking();

  /** Returns the list of users that were muted by the authenticating user. */
  Sendable<GraphResponse> muting();

  /** Returns the list of users the authenticated user is following. */
  Sendable<GraphResponse> following();

  /** Returns the list of users that are following the authenticated user. */
  Sendable<GraphResponse> followers();

  /** Returns the list of ticker symbols publically followed by the authenticating user. */
  Sendable<GraphResponse> symbols();
}
