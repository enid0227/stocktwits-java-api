package com.stocktwitlist.api.exception;

import javax.annotation.Nullable;

/** Custom Exception for a failed api request */
public class StocktwitsApiException extends Exception {
  private Integer statusCode;
  private String response;

  public StocktwitsApiException(String message) {
    super(message);
  }

  public StocktwitsApiException(Throwable cause) {
    super(cause);
  }

  public StocktwitsApiException(String message, Throwable cause) {
    super(message, cause);
  }

  /** Returns the statusCode from a failed request made to stocktwits.com */
  @Nullable
  public Integer getStatusCode() {
    return statusCode;
  }

  public StocktwitsApiException setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /** Returns the response from a failed request made to stocktwtis.com */
  @Nullable
  public String getResposne() {
    return response;
  }

  public StocktwitsApiException setResponse(String response) {
    this.response = response;
    return this;
  }
}
