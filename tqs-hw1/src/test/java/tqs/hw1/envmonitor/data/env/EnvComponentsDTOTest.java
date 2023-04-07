package tqs.hw1.envmonitor.data.env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnvComponentsDTOTest {

    EnvComponentsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    @Test
    void getters() {
        assertThat(dto.getCo()).isEqualTo(0.0);
        assertThat(dto.getNh3()).isEqualTo(0.0);
        assertThat(dto.getNo()).isEqualTo(0.0);
        assertThat(dto.getNo2()).isEqualTo(0.0);
        assertThat(dto.getO3()).isEqualTo(0.0);
        assertThat(dto.getPm10()).isEqualTo(0.0);
        assertThat(dto.getPm2_5()).isEqualTo(0.0);
        assertThat(dto.getSo2()).isEqualTo(0.0);
    }

    @Test
    void setters() {
        dto.setCo(1.0);
        dto.setNh3(1.0);
        dto.setNo(1.0);
        dto.setNo2(1.0);
        dto.setO3(1.0);
        dto.setPm10(1.0);
        dto.setPm2_5(1.0);
        dto.setSo2(1.0);

        assertThat(dto.getCo()).isEqualTo(1.0);
        assertThat(dto.getNh3()).isEqualTo(1.0);
        assertThat(dto.getNo()).isEqualTo(1.0);
        assertThat(dto.getNo2()).isEqualTo(1.0);
        assertThat(dto.getO3()).isEqualTo(1.0);
        assertThat(dto.getPm10()).isEqualTo(1.0);
        assertThat(dto.getPm2_5()).isEqualTo(1.0);
        assertThat(dto.getSo2()).isEqualTo(1.0);
    }
}