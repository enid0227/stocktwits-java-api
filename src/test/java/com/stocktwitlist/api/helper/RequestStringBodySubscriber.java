package com.stocktwitlist.api.helper;

import java.net.http.HttpResponse.BodySubscriber;
import java.nio.ByteBuffer;
import java.util.List;
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
}
