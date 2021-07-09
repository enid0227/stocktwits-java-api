# Stocktwits Client Library for Java

![ci workflow](https://github.com/enid0227/stocktwits-java-api/actions/workflows/gradle-java-ci.yml/badge.svg)

## Description

The `stocktwits-java-api` is a [stocktwits api](https://api.stocktwits.com/developers/docs) client
inspired by [fluent interface](https://en.wikipedia.org/wiki/Fluent_interface). This client does
not handle OAuth authorization/authentication with stocktwits.

To use this client in your java project, simply provide the access token that's obtained from other
OAuth library (such as [Spring Security](https://docs.spring.io/spring-security/site/docs/current/reference/html5/#oauth2))
to a [StocktwitsClient](https://github.com/enid0227/stocktwits-java-api/blob/main/src/main/java/com/stocktwitlist/api/client/StocktwitsClient.java)
instance (either singleton or new instance is fine).

## How to Use this Client Project

### Example Spring Boot Project: [spring-boot-stocktwits-starter](https://github.com/enid0227/spring-boot-stocktwits-starter)

### With Maven

Add the `jitpack` repository and `stocktwits-java-api` in the `pom.xml`

```xml
...
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
...
<dependency>
    <groupId>com.github.enid0227</groupId>
    <artifactId>stocktwits-java-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

### With Gradle

Add the `jitpack` repository and `stocktwits-java-api` in the `build.gradle`

```gradle
...
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
...
dependencies {
        implementation 'com.github.enid0227:stocktwits-java-api:1.0.0'
}
```
### With `sbt` or `leiningen`

Please see https://jitpack.io/#enid0227/stocktwits-java-api/v1.0.0

## Example Usage

```java
// Example Call https://api.stocktwits.com/api/2/streams/user/:id.json
StreamResponse resp =
    new StocktwitsClient()
        .newRequest("testToken")
        .streams()
        .setSince(1L)
        .setMax(1000000L)
        .setLimit(25)
        .user("testusername")
        .send();
```

**Example Usage with a StocktwitsClient as a dependency**

```java
class MyService {

  private final StocktwitsClient stocktwitsClient;
  
  // inject StocktwitsClient via constructor
  MyService(StocktwitsClient stocktwitsClient) { this.stocktwitsClient = stocktwitsClient; }

  void printSearchResponse() {
    SearchResponse resp =
        stocktwitsClient.newRequest("testToken")
            .search()
            .users("findingnemo")
            .send();
    System.out.println(resp);
  }
}
```

To see more example usage, please see [test cases](https://github.com/enid0227/stocktwits-java-api/tree/main/src/test/java/com/stocktwitlist/api/client)


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

### Mockito

[Mockito](https://site.mockito.org/) is used to stub/mock `HttpClient` in the tests

### google-java-format

[google-java-format](https://github.com/google/google-java-format) is used to format all Java
sources in this project with
the [IntelliJ plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format).
