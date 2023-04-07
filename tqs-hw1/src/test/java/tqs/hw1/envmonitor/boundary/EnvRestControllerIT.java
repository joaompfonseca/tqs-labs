package tqs.hw1.envmonitor.boundary;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnvRestControllerIT {

    @LocalServerPort
    int localPort;

    @Test
    void givenAveiro_whenGetCurrentEnv_thenStatus200() {
        String location = "Aveiro";

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "env", "current")
                .queryParam("q", location)
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(200)
                .body("location", is("Aveiro"))
                .body("country", is("PT"))
                .body("items.size()", is(1));
    }

    @Test
    void givenInvalidLocation_whenGetCurrentEnv_thenStatus404() {
        String location = "InvalidLocation";

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "env", "current")
                .queryParam("q", location)
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(404);
    }

    @Test
    void givenAveiro_whenGetForecastEnv_thenStatus200() {
        String location = "Aveiro";

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "env", "forecast")
                .queryParam("q", location)
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(200)
                .body("location", is("Aveiro"))
                .body("country", is("PT"))
                .body("items.size()", greaterThanOrEqualTo(1));
    }

    @Test
    void givenInvalidLocation_whenGetForecastEnv_thenStatus404() {
        String location = "InvalidLocation";

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "env", "forecast")
                .queryParam("q", location)
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(404);
    }
}
