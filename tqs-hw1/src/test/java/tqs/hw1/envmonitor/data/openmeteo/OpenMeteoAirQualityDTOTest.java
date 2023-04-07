package tqs.hw1.envmonitor.data.openmeteo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenMeteoAirQualityDTOTest {

    OpenMeteoAirQualityDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OpenMeteoAirQualityDTO();
    }

    @Test
    void getters() {
        assertThat(dto.getHourly()).isNull();
    }

    @Test
    void setters() {
        dto.setHourly(new OpenMeteoAirQualityHourlyDTO());

        assertThat(dto.getHourly()).isNotNull();
    }
}