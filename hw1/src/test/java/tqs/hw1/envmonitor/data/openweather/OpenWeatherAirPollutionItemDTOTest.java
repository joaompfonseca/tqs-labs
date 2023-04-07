package tqs.hw1.envmonitor.data.openweather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenWeatherAirPollutionItemDTOTest {

    OpenWeatherAirPollutionItemDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OpenWeatherAirPollutionItemDTO(0L, new OpenWeatherAirPollutionComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
    }

    @Test
    void getters() {
        assertThat(dto.getDt()).isEqualTo(0L);
        assertThat(dto.getComponents()).extracting("co", "nh3", "no", "no2", "o3", "pm10", "pm2_5", "so2")
                .isEqualTo(List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
    }

    @Test
    void setters() {
        dto.setDt(1L);
        dto.setComponents(new OpenWeatherAirPollutionComponentsDTO(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));

        assertThat(dto.getDt()).isEqualTo(1L);
        assertThat(dto.getComponents()).extracting("co", "nh3", "no", "no2", "o3", "pm10", "pm2_5", "so2")
                .isEqualTo(List.of(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0));
    }

}