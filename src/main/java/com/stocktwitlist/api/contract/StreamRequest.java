package com.stocktwitlist.api.contract;

import com.stocktwitlist.api.value.StreamResponse;
import java.util.List;

/** Collection of all streams API endpoints. */
public interface StreamRequest extends Pageable<StreamRequest> {
  /** Returns the most recent 30 messages for the specified user. */
  Sendable<StreamResponse> user(long userId);

  /** Returns the most recent 30 messages for the specified user. */
  Sendable<StreamResponse> user(String username);

  /** Returns the most recent 30 messages for the specified symbol. */
  Sendable<StreamResponse> symbol(long symbolId);

  /** Returns the most recent 30 messages for the specified symbol. */
  Sendable<StreamResponse> symbol(String symbol);

  /**
   * Returns the most recent 30 messages posted by people followed by tthe the authenticating user.
   */
  Sendable<StreamResponse> friends();

  /**
   * Returns the most recent 30 messages containing mentions of the authenticating user's handle.
   */
  Sendable<StreamResponse> mentions();

  /** Returns the most recent 30 direct messages sent to the authenticating user. */
  Sendable<StreamResponse> direct();

  /** Returns the most recent 30 direct messages sent from the authenticating user. */
  Sendable<StreamResponse> directSent();

  /** Returns the most recent 30 direct messages received by the authenticating user. */
  Sendable<StreamResponse> directReceived();

  /**
   * Returns the most recent 30 messages for the specified watch list for the authenticating user.
   */
  Sendable<StreamResponse> watchlist();

  /**
   * Returns the most recent 30 messages for all non-private symbols.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> all();

  /** Returns the most recent 30 messages that include charts. */
  Sendable<StreamResponse> charts();

  /**
   * Returns the most recent 30 messages containing equity symbols.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> equities();

  /**
   * Returns the most recent 30 messages for forex symbols.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> forex();

  /**
   * Returns the most recent 30 messages for futures symbols.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> futures();

  /**
   * Returns the most recent 30 messages containing private symbols.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> private_companies();

  /**
   * Returns the most recent 30 messages from our suggested users, a curated list of quality
   * Stocktwits contributors.
   */
  Sendable<StreamResponse> suggested();

  /**
   * Returns the most recent 30 messages for the specified list of symbols.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> symbols(List<String> symbols);

  /** Returns the most recent 30 messages with trending symbols in the last 5 minutes. */
  Sendable<StreamResponse> trending();

  /**
   * This returns the most recent 30 messages of the passed sector path.
   *
   * <p>Requires partner-level access
   */
  Sendable<StreamResponse> sectors(String sector);

  /** Returns the most recent 30 messages for the specified conversation. */
  Sendable<StreamResponse> conversation(long conversationId);
}
