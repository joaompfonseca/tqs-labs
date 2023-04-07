package tqs.hw1.envmonitor.boundary;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebControllerIT {

    @LocalServerPort
    int localPort;

    @Autowired
    private MockMvc mvc;

    @Test
    void whenIndex_thenEmptyIndex() throws Exception {
        mvc.perform(get("/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("location_country", nullValue()))
                .andExpect(model().attribute("env_current", nullValue()))
                .andExpect(model().attribute("env_forecast", nullValue()));
    }

    @Test
    void givenNullQuery_whenSearch_thenEmptyIndex() throws Exception {
        String query = ""; // Maps to null in the Controller

        mvc.perform(get("/search").param("q", query).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("location_country", nullValue()))
                .andExpect(model().attribute("env_current", nullValue()))
                .andExpect(model().attribute("env_forecast", nullValue()));
    }

    @Test
    void givenEmptyQuery_whenSearch_thenEmptyIndex() throws Exception {
        String query = " ";

        mvc.perform(get("/search").param("q", query).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("location_country", nullValue()))
                .andExpect(model().attribute("env_current", nullValue()))
                .andExpect(model().attribute("env_forecast", nullValue()));
    }

    @Test
    void givenInvalidLocation_whenSearch_thenEmptyIndex() throws Exception {
        String query = "InvalidLocation";

        mvc.perform(get("/search").param("q", query).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("query", "InvalidLocation"))
                .andExpect(model().attribute("location_country", nullValue()))
                .andExpect(model().attribute("env_current", nullValue()))
                .andExpect(model().attribute("env_forecast", nullValue()));
    }

    @Test
    void givenAveiro_whenSearch_thenResultsIndex() throws Exception {
        String query = "Aveiro";

        mvc.perform(get("/search").param("q", query).contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("query", "Aveiro"))
                .andExpect(model().attribute("location_country", "Aveiro, PT"))
                .andExpect(model().attribute("env_current", notNullValue()))
                .andExpect(model().attribute("env_forecast", notNullValue()));
    }
}
