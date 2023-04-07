package tqs.hw1.envmonitor.boundary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.env.EnvItemDTO;
import tqs.hw1.envmonitor.service.EnvService;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WebController.class)
public class WebControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EnvService envService;

    @BeforeEach
    void setUp() {
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
            .andExpect(model().attribute("location_country", "Aveiro, PT"));
    }
}
