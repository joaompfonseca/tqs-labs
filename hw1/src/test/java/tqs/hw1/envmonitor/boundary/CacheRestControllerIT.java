package tqs.hw1.envmonitor.boundary;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheRestControllerIT {

    @LocalServerPort
    int localPort;

    @Autowired
    Environment env;

    @Test
    void givenEnvCache_whenGetStats_thenStatus200() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "cache", "stats")
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(200)
                .body("ttl", is(env.getProperty("cache.ttl", Integer.class)))
                .body("capacity", is(env.getProperty("cache.capacity", Integer.class)));
    }
}
