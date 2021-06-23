# Stocktwits Client Library for Java

## Description

The `stocktwits-java-api` is a [stocktwits api](https://api.stocktwits.com/developers/docs) client
inspired by [fluent interface](https://en.wikipedia.org/wiki/Fluent_interface).

**Example Usage**

```java
// Example Call https://api.stocktwits.com/api/2/streams/user/:id.json
StreamResponse resp=
    StocktwitsApiClient.newRequest("testToken")
    .streams()
    .setSince(1L)
    .setMax(1000000L)
    .setLimit(25)
    .user("testusername")
    .send();
```

## Project Dependencies

### AutoValue

[AutoValue](https://github.com/google/auto/blob/master/value/userguide/index.md) is used to
represent the JSON response in Java. It generates critical boilerplate methods (such
as `Equals(), hashCode()`...etc)
See more [benefits here](https://github.com/google/auto/blob/master/value/userguide/why.md)

### Jackson

[Jackson](https://github.com/FasterXML/jackson) is the JSON library for java. This project uses
jackson library handle the serialize/deserialize between JSON String and AutoValue Java Object.

### Truth + JUnit5

[Truth](https://truth.dev/) provides easy-to-read assertion API for java JUnit5 tests.

### google-java-format

[google-java-format](https://github.com/google/google-java-format) is used to format all Java
sources in this project with
the [IntelliJ plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format).