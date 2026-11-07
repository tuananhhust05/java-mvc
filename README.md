# java-mvc: Post Service

A Spring Boot microservice that handles posts in the social feed of an e-wallet app. Users can view a post, pin it, save it, hide or delete it, and tag colleagues. Each request is authenticated with a JWT, and its payload can be AES-encrypted with a per-device key.

## Features

| Endpoint (POST) | Purpose |
| --- | --- |
| `/getPostDetail` | Return a post with its content, images, category, like and comment counts, tagged users and poster |
| `/pinPost` | Pin a post for a date range (`fromDate`, `toDate`) |
| `/SAVE_UNSAVE_POST` | Save or unsave a post (`type`: `SAVE` / `UNSAVE`) |
| `/deleteOrHidePost` | Owners hide or delete their own post; admins remove a post with a reason (`function`: `HIDE` / `DELETE` / `REMOVE`) |
| `/GET_TAG_LIST` | Search colleagues by name, paginated, to tag in a post |

All endpoints:

* expect `Authorization: Bearer <JWT>`, whose subject is the user's phone number;
* read `requestid`, `language` and the device ID from the headers;
* decrypt the request body with the AES key stored in Redis for that device, unless `?encrypted=false` is passed;
* return a common envelope with an error code and a message localised from an error-code table.

## Tech stack

| Layer | Technology |
| --- | --- |
| Framework | Java 8, Spring Boot 2.6, Spring MVC, Spring Security |
| Auth | JWT (jjwt, HS512), stateless sessions |
| Posts, comments, reactions | MongoDB (Spring Data MongoDB) |
| Users, companies, error codes | Oracle via MyBatis, plus Spring Data JPA |
| Colleague search | Elasticsearch (Spring Data Elasticsearch) |
| Device keys | Redis (Jedis) |
| Crypto | AES, and RSA via Bouncy Castle |
| Build | Maven wrapper |

## Project layout

```text
src/main/java/com/epay/ewallet/service/post/
  authen/          JWT filter, token utility, security configuration
  config/          data sources, MyBatis, MongoDB
  controller/      REST endpoints
  service/         business logic (interfaces and impl/)
  repository/      MongoDB, JPA and Elasticsearch repositories
  mapperDataOne/   MyBatis mappers and XML
  model/           domain models
  payloads/        request and response DTOs
  securities/      AES and RSA encryption
  redis/           Redis configuration and access
  exception/       global exception handling
  utils/           request decoding and helpers
```

## Configuration

`src/main/resources/application.properties` is intentionally empty. Provide your own values, for example:

```properties
spring.application.name=post-service
server.port=8080
jwt.secret=<jwt-signing-secret>

REDIS_HOST=<host>
REDIS_PORT=6379
REDIS_AUTH=<0-or-1>
REDIS_PASSWORD=<password>
REDIS_DB=0
redis.max-total=<n>
redis.max_idle=<n>
redis.min-idle=<n>
redis.max-wait=<ms>
redis.min-evictable-idle-time=<ms>
redis.time-between-eviction-runs=<ms>

spring.datasource.one.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.one.jdbc-url=jdbc:oracle:thin:@<host>:1521:<sid>
spring.datasource.one.username=<user>
spring.datasource.one.password=<password>
spring.datasource.two.driver-class-name=<driver>
spring.datasource.two.jdbc-url=<jdbc-url>
spring.datasource.two.username=<user>
spring.datasource.two.password=<password>

spring.data.mongodb.uri_=mongodb://<user>:<password>@<host>:27017/<db>?authSource=admin
spring.data.mongodb.database_=<db>
spring.data.mongodb.minSize_=<n>
spring.data.mongodb.maxSize_=<n>
spring.data.mongodb.maxConnecting_=<n>
spring.data.mongodb.maxConnectionIdleTime_=<s>
spring.data.mongodb.maxConnectionLifeTime_=<s>
spring.data.mongodb.maxWaitTime_=<s>

spring.elasticsearch.uris=http://<host>:9200
```

Do not commit real credentials.

## Build and run

Requires JDK 8 or later, and reachable MongoDB, Oracle, Redis and Elasticsearch instances.

```bash
./mvnw clean package -DskipTests
java -jar target/post-service-0.0.1.jar
```

The only test, `PostServiceApplicationTests.contextLoads`, starts the full application context, so it needs the databases above.

## License

[MIT](LICENSE)
