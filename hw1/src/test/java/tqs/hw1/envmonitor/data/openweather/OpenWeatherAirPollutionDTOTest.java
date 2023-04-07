package tqs.hw1.envmonitor.data.openweather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherAirPollutionDTOTest {

    OpenWeatherAirPollutionDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OpenWeatherAirPollutionDTO();
    }

    @Test
    void getters() {
        assertThat(dto.getList()).isNull();
    }

    @Test
    void setters() {
        dto.setList(new ArrayList<>());

        assertThat(dto.getList()).isEqualTo(new ArrayList<>());
    }

}