package tqs.hw1.envmonitor.data.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheStatsDTOTest {

    CacheStatsDTO dto;

    @BeforeEach
    void setUp() {
        dto = new CacheStatsDTO(0L, 0, 0, 0, 0, 0);
    }

    @Test
    void getters() {
        assertThat(dto.getTtl()).isEqualTo(0L);
        assertThat(dto.getCapacity()).isEqualTo(0);
        assertThat(dto.getNRequests()).isEqualTo(0);
        assertThat(dto.getNHits()).isEqualTo(0);
        assertThat(dto.getNMisses()).isEqualTo(0);
        assertThat(dto.getNItems()).isEqualTo(0);
    }

    @Test
    void setters() {
        dto.setTtl(1L);
        dto.setCapacity(1);
        dto.setNRequests(1);
        dto.setNHits(1);
        dto.setNMisses(1);
        dto.setNItems(1);

        assertThat(dto.getTtl()).isEqualTo(1L);
        assertThat(dto.getCapacity()).isEqualTo(1);
        assertThat(dto.getNRequests()).isEqualTo(1);
        assertThat(dto.getNHits()).isEqualTo(1);
        assertThat(dto.getNMisses()).isEqualTo(1);
        assertThat(dto.getNItems()).isEqualTo(1);
    }
}