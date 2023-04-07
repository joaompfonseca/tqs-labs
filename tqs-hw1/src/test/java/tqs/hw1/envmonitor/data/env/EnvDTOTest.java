package tqs.hw1.envmonitor.data.env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class EnvDTOTest {

    EnvDTO dto;

    @BeforeEach
    void setUp() {
        dto = new EnvDTO("Aveiro", "PT", null);
    }

    @Test
    void getters() {
        assertThat(dto.getLocation()).isEqualTo("Aveiro");
        assertThat(dto.getCountry()).isEqualTo("PT");
        assertThat(dto.getItems()).isNull();
    }

    @Test
    void setters() {
        dto.setLocation("Madrid");
        dto.setCountry("ES");
        dto.setItems(new ArrayList<>());

        assertThat(dto.getLocation()).isEqualTo("Madrid");
        assertThat(dto.getCountry()).isEqualTo("ES");
        assertThat(dto.getItems()).isEqualTo(new ArrayList<>());
    }
}