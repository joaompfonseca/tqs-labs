package tqs.lab6;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TodosTest {
    @Test
    void whenGetAll_thenStatus200() {
        given()
        .when()
            .get("https://jsonplaceholder.typicode.com/todos")
        .then()
            .statusCode(200);
    }

    @Test
    void whenGetId4_thenTitleEtPorroTempora() {
        given()
        .when()
            .get("https://jsonplaceholder.typicode.com/todos/4")
        .then()
            .statusCode(200)
            .body("title", equalTo("et porro tempora"));
    }

    @Test
    void whenGetAll_thenId198AndId199Exist() {
        given()
        .when()
            .get("https://jsonplaceholder.typicode.com/todos")
        .then()
            .statusCode(200)
            .body("id", hasItems(198, 199));
    }

    @Test
    void whenGetAll_thenResponseLessThan2Seconds() {
        given()
        .when()
            .get("https://jsonplaceholder.typicode.com/todos")
        .then()
            .statusCode(200)
            .time(lessThan(2000L));
    }
}
