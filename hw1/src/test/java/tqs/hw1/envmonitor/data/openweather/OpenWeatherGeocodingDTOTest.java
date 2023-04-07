package tqs.hw1.envmonitor.data.openweather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherGeocodingDTOTest {

    OpenWeatherGeocodingDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OpenWeatherGeocodingDTO(0.0, 0.0, "Aveiro", "PT");
    }

    @Test
    void getters() {
        assertThat(dto.getLat()).isEqualTo(0.0);
        assertThat(dto.getLon()).isEqualTo(0.0);
        assertThat(dto.getLocation()).isEqualTo("Aveiro");
        assertThat(dto.getCountry()).isEqualTo("PT");
    }

    @Test
    void setters() {
        dto.setLat(1.0);
        dto.setLon(1.0);
        dto.setLocation("Porto");
        dto.setCountry("PT");

        assertThat(dto.getLat()).isEqualTo(1.0);
        assertThat(dto.getLon()).isEqualTo(1.0);
        assertThat(dto.getLocation()).isEqualTo("Porto");
        assertThat(dto.getCountry()).isEqualTo("PT");
    }
}