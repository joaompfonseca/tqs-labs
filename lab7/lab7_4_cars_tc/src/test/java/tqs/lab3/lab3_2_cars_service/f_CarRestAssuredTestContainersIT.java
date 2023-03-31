package tqs.lab3.lab3_2_cars_service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create") // Alternative to Flyway
public class f_CarRestAssuredTestContainersIT {

    @Container
    private static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("user")
            .withPassword("pass")
            .withDatabaseName("car_service");

    @LocalServerPort
    int localPort;
    Car opel, honda, mazda;

    @Autowired
    private CarRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        opel = repository.save(new Car("Opel", "Astra"));
        honda = repository.save(new Car("Honda", "Civic"));
        mazda = repository.save(new Car("Mazda", "3"));
    }

    @Test
    public void whenValidInput_thenCreateCar() throws Exception {
        Car tesla = new Car("Tesla", "Model 3");

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured
            .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(JsonUtils.toJson(tesla))
            .when()
                .post(endpoint)
            .then()
                .statusCode(201)
                .body("maker", is(tesla.getMaker()))
                .body("model", is(tesla.getModel()));
    }

    @Test
    void whenValidId_thenCarShouldExist() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "cars", opel.getCarId().toString())
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(200)
                .body("maker", is(opel.getMaker()))
                .body("model", is(opel.getModel()));
    }

    @Test
    void given3Cars_whenGetAll_thenStatus200() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPort)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured
            .given()
            .when()
                .get(endpoint)
            .then()
                .statusCode(200)
                .body("$.size()", is(3))
                .body("maker", hasItems("Opel", "Honda", "Mazda"))
                .body("model", hasItems("Astra", "Civic", "3"));
    }
}
