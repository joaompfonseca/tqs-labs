package tqs.hw1.envmonitor.boundary;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.env.EnvItemDTO;
import tqs.hw1.envmonitor.service.EnvService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@WebMvcTest(EnvRestController.class)
public class EnvRestControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EnvService envService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mvc);
        // Current Env
        when(envService.getCurrentEnv("Aveiro"))
                .thenReturn(new EnvDTO("Aveiro", "PT", List.of(new EnvItemDTO(1680392189000L, 4, new EnvComponentsDTO(216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)))));
        when(envService.getCurrentEnv("InvalidLocation"))
                .thenReturn(null);
        // Forecast Env
        when(envService.getForecastEnv("Aveiro"))
                .thenReturn(new EnvDTO("Aveiro", "PT", List.of(new EnvItemDTO(1680392189000L, 4, new EnvComponentsDTO(216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)))));
        when(envService.getForecastEnv("InvalidLocation"))
                .thenReturn(null);
    }

    @Test
    void givenAveiro_whenGetCurrentEnv_thenStatus200() {
        String location = "Aveiro";

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/env/current?q={location}", location)
            .then()
                .statusCode(200)
                .body("location", is("Aveiro"))
                .body("country", is("PT"))
                .body("items.size()", is(1))
                .body("items[0].dt", is(1680392189000L))
                .body("items[0].aqi", is(4))
                .body("items[0].components.co", is(216.96f))
                .body("items[0].components.nh3", is(0.78f))
                .body("items[0].components.no", is(0.0f))
                .body("items[0].components.no2", is(2.25f))
                .body("items[0].components.o3", is(98.71f))
                .body("items[0].components.pm10", is(3.76f))
                .body("items[0].components.pm2_5", is(1.87f))
                .body("items[0].components.so2", is(1.85f));
    }

    @Test
    void givenInvalidLocation_whenGetCurrentEnv_thenStatus404() {
        String location = "InvalidLocation";

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/env/current?q={location}", location)
            .then()
                .statusCode(404);
    }

    @Test
    void givenAveiro_whenGetForecastEnv_thenStatus200() {
        String location = "Aveiro";

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/env/forecast?q={location}", location)
            .then()
                .statusCode(200)
                .body("location", is("Aveiro"))
                .body("country", is("PT"))
                .body("items.size()", is(1))
                .body("items[0].dt", is(1680392189000L))
                .body("items[0].aqi", is(4))
                .body("items[0].components.co", is(216.96f))
                .body("items[0].components.nh3", is(0.78f))
                .body("items[0].components.no", is(0.0f))
                .body("items[0].components.no2", is(2.25f))
                .body("items[0].components.o3", is(98.71f))
                .body("items[0].components.pm10", is(3.76f))
                .body("items[0].components.pm2_5", is(1.87f))
                .body("items[0].components.so2", is(1.85f));
    }

    @Test
    void givenInvalidLocation_whenGetForecastEnv_thenStatus404() {
        String location = "InvalidLocation";

        RestAssuredMockMvc
            .given()
            .when()
                .get("/api/env/forecast?q={location}", location)
            .then()
                .statusCode(404);
    }
}
