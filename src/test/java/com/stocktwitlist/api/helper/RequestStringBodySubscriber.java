package com.stocktwitlist.api.helper;

import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.BodySubscribers;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow;

/**
 * Adapter to use response body subscriber as request body subscriber
 *
 * <pre>
 *  Flow.Subscriber<ByteBuffer> - request subscriber
 *    Flow.Subscriber<List<ByteBuffer>> - response subscriber
 * </pre>
 */
public class RequestStringBodySubscriber implements Flow.Subscriber<ByteBuffer> {
  private final BodySubscriber<String> bodySubscriber;

  /** Default constructor with built-in standard string body subscriber. */
  public RequestStringBodySubscriber() {
    this.bodySubscriber = BodySubscribers.ofString(StandardCharsets.UTF_8);
  }

  /**
   * Construct an instance with an injected BodySubscriber
   *
   * <p>use when concurrent or other reactive verification is needed.
   */
  public RequestStringBodySubscriber(BodySubscriber<String> bodySubscriber) {
    this.bodySubscriber = bodySubscriber;
  }

  @Override
  public void onSubscribe(Flow.Subscription subscription) {
    bodySubscriber.onSubscribe(subscription);
  }

  @Override
  public void onNext(ByteBuffer item) {
    bodySubscriber.onNext(List.of(item));
  }

  @Override
  public void onError(Throwable throwable) {
    bodySubscriber.onError(throwable);
  }

  @Override
  public void onComplete() {
    bodySubscriber.onComplete();
  }

  /** Return body string synchronously */
  public String getBodyString() throws InterruptedException, ExecutionException {
    return bodySubscriber.getBody().toCompletableFuture().get();
  }
}
