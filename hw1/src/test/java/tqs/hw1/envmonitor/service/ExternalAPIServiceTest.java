package tqs.hw1.envmonitor.service;

import feign.FeignException;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityDTO;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityHourlyDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionComponentsDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionItemDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherGeocodingDTO;
import tqs.hw1.envmonitor.external.openmeteo.OpenMeteoAirQualityAPI;
import tqs.hw1.envmonitor.external.openweather.OpenWeatherAirPollutionAPI;
import tqs.hw1.envmonitor.external.openweather.OpenWeatherGeocodingAPI;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ExternalAPIServiceTest {

    @Mock(lenient = true)
    private OpenWeatherGeocodingAPI openWeatherGeocodingAPI;
    @Mock(lenient = true)
    private OpenWeatherAirPollutionAPI openWeatherAirPollutionAPI;
    @Mock(lenient = true)
    private OpenMeteoAirQualityAPI openMeteoAirQualityAPI;
    @InjectMocks
    private ExternalAPIService externalAPIService;

    @BeforeEach
    void setUp() {
        // OpenWeather Geocoding API
        when(openWeatherGeocodingAPI.getCoordinates("Aveiro"))
                .thenReturn(List.of(new OpenWeatherGeocodingDTO(40.640496, -8.6537841, "Aveiro", "PT")));
        when(openWeatherGeocodingAPI.getCoordinates("AvailableOnlyForOpenWeather"))
                .thenReturn(List.of(new OpenWeatherGeocodingDTO(1.1, 1.1, "AvailableOnlyForOpenWeather", "XX")));
        when(openWeatherGeocodingAPI.getCoordinates("AvailableOnlyForOpenMeteo"))
                .thenReturn(List.of(new OpenWeatherGeocodingDTO(2.2, 2.2, "AvailableOnlyForOpenMeteo", "XX")));
        when(openWeatherGeocodingAPI.getCoordinates("InvalidLocation"))
                .thenReturn(List.of());

        // OpenWeather Air Pollution API
        when(openWeatherAirPollutionAPI.getCurrent(40.640496, -8.6537841))
                .thenReturn(new OpenWeatherAirPollutionDTO(List.of(new OpenWeatherAirPollutionItemDTO(1680392189L, new OpenWeatherAirPollutionComponentsDTO(216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)))));
        when(openWeatherAirPollutionAPI.getCurrent(1.1, 1.1))
                .thenReturn(new OpenWeatherAirPollutionDTO(List.of()));
        when(openWeatherAirPollutionAPI.getCurrent(2.2, 2.2))
                .thenThrow(FeignException.class);

        when(openWeatherAirPollutionAPI.getForecast(40.640496, -8.6537841))
                .thenReturn(new OpenWeatherAirPollutionDTO(List.of(new OpenWeatherAirPollutionItemDTO(1680392189L, new OpenWeatherAirPollutionComponentsDTO(216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)))));
        when(openWeatherAirPollutionAPI.getForecast(1.1, 1.1))
                .thenReturn(new OpenWeatherAirPollutionDTO(List.of()));
        when(openWeatherAirPollutionAPI.getForecast(2.2, 2.2))
                .thenThrow(FeignException.class);

        // OpenMeteo Air Quality API
        when(openMeteoAirQualityAPI.getForecast(1.1, 1.1))
                .thenThrow(FeignException.class);
        when(openMeteoAirQualityAPI.getForecast(2.2, 2.2))
                .thenReturn(new OpenMeteoAirQualityDTO(new OpenMeteoAirQualityHourlyDTO(List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), List.of())));
    }

    @Test
    void givenAveiro_whenGetCurrentEnv_thenAveiroEnv() {
        String location = "Aveiro";

        EnvDTO currentEnv = externalAPIService.getCurrentEnv(location);

        assertThat(currentEnv.getLocation()).isEqualTo("Aveiro");
        assertThat(currentEnv.getCountry()).isEqualTo("PT");
        assertThat(currentEnv.getItems())
                .hasSize(1)
                .extracting("dt", "components.co", "components.nh3", "components.no", "components.no2", "components.o3", "components.pm10", "components.pm2_5", "components.so2")
                .isEqualTo(List.of(Tuple.tuple(1000*1680392189L, 216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)));
    }

    @Test
    void givenAveiro_whenGetForecastEnv_thenAveiroEnv() {
        String location = "Aveiro";

        EnvDTO forecastEnv = externalAPIService.getForecastEnv(location);

        assertThat(forecastEnv.getLocation()).isEqualTo("Aveiro");
        assertThat(forecastEnv.getCountry()).isEqualTo("PT");
        assertThat(forecastEnv.getItems())
                .hasSize(1)
                .extracting("dt", "components.co", "components.nh3", "components.no", "components.no2", "components.o3", "components.pm10", "components.pm2_5", "components.so2")
                .isEqualTo(List.of(Tuple.tuple(1000*1680392189L, 216.96, 0.78, 0.0, 2.25, 98.71, 3.76, 1.87, 1.85)));
    }

    @Test
    void givenAvailableOnlyForOpenWeather_whenGetCurrentEnv_thenEnvDTO() {
        String location = "AvailableOnlyForOpenWeather";

        EnvDTO currentEnv = externalAPIService.getCurrentEnv(location);

        assertThat(currentEnv.getLocation()).isEqualTo("AvailableOnlyForOpenWeather");
        assertThat(currentEnv.getCountry()).isEqualTo("XX");
    }

    @Test
    void givenAvailableOnlyForOpenMeteo_whenGetCurrentEnv_thenEnvDTO() {
        String location = "AvailableOnlyForOpenMeteo";

        EnvDTO currentEnv = externalAPIService.getCurrentEnv(location);

        assertThat(currentEnv.getLocation()).isEqualTo("AvailableOnlyForOpenMeteo");
        assertThat(currentEnv.getCountry()).isEqualTo("XX");
    }

    @Test
    void givenInvalidLocation_whenGetCurrentEnv_thenNull() {
        String location = "InvalidLocation";

        EnvDTO currentEnv = externalAPIService.getCurrentEnv(location);

        assertThat(currentEnv).isNull();
    }
}
