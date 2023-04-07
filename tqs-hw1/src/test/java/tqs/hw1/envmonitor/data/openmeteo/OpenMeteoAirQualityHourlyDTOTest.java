package tqs.hw1.envmonitor.data.openmeteo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OpenMeteoAirQualityHourlyDTOTest {

    OpenMeteoAirQualityHourlyDTO dto;

    @BeforeEach
    void setUp() {
        dto = new OpenMeteoAirQualityHourlyDTO(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void getters() {
        assertThat(dto.getTime()).isEqualTo(new ArrayList<>());
        assertThat(dto.getAmmonia()).isEqualTo(new ArrayList<>());
        assertThat(dto.getCarbon_monoxide()).isEqualTo(new ArrayList<>());
        assertThat(dto.getNitrogen_dioxide()).isEqualTo(new ArrayList<>());
        assertThat(dto.getOzone()).isEqualTo(new ArrayList<>());
        assertThat(dto.getPm10()).isEqualTo(new ArrayList<>());
        assertThat(dto.getPm2_5()).isEqualTo(new ArrayList<>());
        assertThat(dto.getSulphur_dioxide()).isEqualTo(new ArrayList<>());
    }

    @Test
    void setters() {
        dto.setTime(new ArrayList<>(List.of(0L)));
        dto.setAmmonia(new ArrayList<>(List.of(0.0)));
        dto.setCarbon_monoxide(new ArrayList<>(List.of(0.0)));
        dto.setNitrogen_dioxide(new ArrayList<>(List.of(0.0)));
        dto.setOzone(new ArrayList<>(List.of(0.0)));
        dto.setPm10(new ArrayList<>(List.of(0.0)));
        dto.setPm2_5(new ArrayList<>(List.of(0.0)));
        dto.setSulphur_dioxide(new ArrayList<>(List.of(0.0)));

        assertThat(dto.getTime()).isEqualTo(new ArrayList<>(List.of(0L)));
        assertThat(dto.getAmmonia()).isEqualTo(new ArrayList<>(List.of(0.0)));
        assertThat(dto.getCarbon_monoxide()).isEqualTo(new ArrayList<>(List.of(0.0)));
        assertThat(dto.getNitrogen_dioxide()).isEqualTo(new ArrayList<>(List.of(0.0)));
        assertThat(dto.getOzone()).isEqualTo(new ArrayList<>(List.of(0.0)));
        assertThat(dto.getPm10()).isEqualTo(new ArrayList<>(List.of(0.0)));
        assertThat(dto.getPm2_5()).isEqualTo(new ArrayList<>(List.of(0.0)));
        assertThat(dto.getSulphur_dioxide()).isEqualTo(new ArrayList<>(List.of(0.0)));
    }

}