package com.stocktwitlist.api.client;

import static java.util.stream.Collectors.joining;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.flogger.FluentLogger;
import com.stocktwitlist.api.contract.Sendable;
import com.stocktwitlist.api.exception.StocktwitsApiException;
import com.stocktwitlist.api.value.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Context {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private final List<String> endpointPath;
  private final Map<String, String> queryParams;
  private final ObjectMapper objectMapper;
  private final Map<String, String> data;
  private HttpClient httpClient;
  private Class<? extends Response> responseClass;
  private String httpMethod;

  Context(String accessToken) {
    queryParams = new HashMap<>();
    queryParams.put("access_token", accessToken);
    data = new HashMap<>();
    endpointPath = new ArrayList<>();
    endpointPath.add("https://api.stocktwits.com/api/2");
    httpMethod = "GET"; // default to GET method
    objectMapper =
        JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .addModule(new GuavaModule())
            .serializationInclusion(Include.NON_NULL)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .build();
  }

  Context setResponseClass(Class<? extends Response> responseClass) {
    this.responseClass = responseClass;
    return this;
  }

  Context appendPath(String path) {
    this.endpointPath.add(path);
    return this;
  }

  Context setSince(long since) {
    queryParams.put("since", "" + since);
    return this;
  }

  Context setMax(long max) {
    queryParams.put("max", "" + max);
    return this;
  }

  Context setLimit(int limit) {
    if (limit > 30) {
      throw new IllegalArgumentException("limit cannot exceed 30");
    }
    queryParams.put("limit", "" + limit);
    return this;
  }

  Context addQueryParam(String key, String value) {
    queryParams.put(key, value);
    return this;
  }

  Context addData(String key, String value) {
    data.put(key, value);
    return this;
  }

  String getUrl() {
    // queryParams;
    StringBuilder urlBuilder = new StringBuilder();
    urlBuilder.append(String.join("/", endpointPath) + ".json");
    urlBuilder.append("?");
    urlBuilder.append(
        queryParams.keySet().stream()
            // make sure param order is always the same for the same combination of
            // params.
            // this makes debug and test easier.
            .sorted()
            .map(key -> key + "=" + URLEncoder.encode(queryParams.get(key), StandardCharsets.UTF_8))
            .collect(joining("&")));
    return urlBuilder.toString();
  }

  String sendRequest() throws StocktwitsApiException {
    String url = getUrl();
    logger.atInfo().log(url);

    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create(url))
              .method(httpMethod, BodyPublishers.ofString(objectMapper.writeValueAsString(data)))
              .build();
      HttpResponse<String> response = getHttpClient().send(request, BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        throw new StocktwitsApiException("bad request to stocktwits.com")
            .setStatusCode(response.statusCode())
            .setResponse(response.body());
      }
      return response.body();
    } catch (IOException | InterruptedException e) {
      throw new StocktwitsApiException(e);
    }
  }

  Response parseJsonString(String json) throws StocktwitsApiException {
    try {
      return objectMapper.readValue(json, responseClass);
    } catch (JsonProcessingException e) {
      throw new StocktwitsApiException("error parsing JSON response", e);
    }
  }

  private HttpClient getHttpClient() {
    return httpClient != null
        ? httpClient
        : HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
  }

  Context setHttpClient(HttpClient httpClient) {
    this.httpClient = httpClient;
    return this;
  }

  Context setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  static class GenericSendable<T extends Response> implements Sendable<T> {
    private final Context context;

    GenericSendable(Context context) {
      this.context = context;
    }

    @Override
    @SuppressWarnings("unchecked") // T always castable bounded by T extends Response
    public T send() throws StocktwitsApiException {
      return (T) context.parseJsonString(context.sendRequest());
    }
  }
}
