package tqs.hw1.envmonitor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.env.EnvItemDTO;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EnvServiceTest {

    @Mock(lenient = true)
    private ExternalAPIService externalAPIService;
    @InjectMocks
    private EnvService envService;

    @BeforeEach
    void setUp() {
        // Current Env
        when(externalAPIService.getCurrentEnv("Aveiro"))
                .thenReturn(new EnvDTO("Aveiro", "PT", List.of(new EnvItemDTO(1680392189000L, 4, new EnvComponentsDTO(216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)))));
        when(externalAPIService.getCurrentEnv("InvalidLocation"))
                .thenReturn(null);
        // Forecast Env
        when(externalAPIService.getForecastEnv("Aveiro"))
                .thenReturn(new EnvDTO("Aveiro", "PT", List.of(new EnvItemDTO(1680392189000L, 4, new EnvComponentsDTO(216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)))));
        when(externalAPIService.getForecastEnv("InvalidLocation"))
                .thenReturn(null);
    }

    @Test
    void givenValidLocation_whenGetCurrentEnv_thenReturnEnvDTO() {
        String location = "Aveiro";

        EnvDTO currentEnv = envService.getCurrentEnv(location);

        assertThat(currentEnv).isInstanceOf(EnvDTO.class);
        assertThat(currentEnv.getLocation()).isEqualTo("Aveiro");
        assertThat(currentEnv.getCountry()).isEqualTo("PT");
    }

    @Test
    void givenInvalidLocation_whenGetCurrentEnv_thenReturnNull() {
        String location = "InvalidLocation";

        EnvDTO currentEnv = envService.getCurrentEnv(location);

        assertThat(currentEnv).isNull();
    }

    @Test
    void givenValidLocation_whenGetForecastEnv_thenReturnEnvDTO() {
        String location = "Aveiro";

        EnvDTO forecastEnv = envService.getForecastEnv(location);

        assertThat(forecastEnv).isInstanceOf(EnvDTO.class);
        assertThat(forecastEnv.getLocation()).isEqualTo("Aveiro");
        assertThat(forecastEnv.getCountry()).isEqualTo("PT");
    }

    @Test
    void givenInvalidLocation_whenGetForecastEnv_thenReturnNull() {
        String location = "InvalidLocation";

        EnvDTO forecastEnv = envService.getForecastEnv(location);

        assertThat(forecastEnv).isNull();
    }
}
