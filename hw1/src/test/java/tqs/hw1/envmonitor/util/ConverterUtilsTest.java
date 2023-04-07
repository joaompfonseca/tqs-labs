package tqs.hw1.envmonitor.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.hw1.envmonitor.cache.Cache;
import tqs.hw1.envmonitor.data.cache.CacheStatsDTO;
import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.env.EnvItemDTO;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityDTO;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityHourlyDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionComponentsDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionItemDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherGeocodingDTO;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


public class ConverterUtilsTest {

    static class IntegerCache extends Cache<String, Integer> {
        public IntegerCache(Long ttl, Integer capacity) {
            super(ttl, capacity);
        }
    }

    private OpenWeatherGeocodingDTO owGeocodingDTO;
    private OpenWeatherAirPollutionDTO owAirPollutionDTO;
    private OpenMeteoAirQualityDTO omAirQualityDTO;

    @BeforeEach
    void setUp() {
        // OpenWeather Geocoding DTO
        owGeocodingDTO = new OpenWeatherGeocodingDTO(40.640496, -8.6537841, "Aveiro", "PT");
        // OpenWeather Air Pollution DTO
        OpenWeatherAirPollutionComponentsDTO[] owAirPollutionComponentsDTOList = new OpenWeatherAirPollutionComponentsDTO[]{
                new OpenWeatherAirPollutionComponentsDTO(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0),
                new OpenWeatherAirPollutionComponentsDTO(10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0)
        };
        OpenWeatherAirPollutionItemDTO[] owAirPollutionItemDTOList = new OpenWeatherAirPollutionItemDTO[]{
                new OpenWeatherAirPollutionItemDTO(0L, owAirPollutionComponentsDTOList[0]),
                new OpenWeatherAirPollutionItemDTO(1L, owAirPollutionComponentsDTOList[1])
        };
        owAirPollutionDTO = new OpenWeatherAirPollutionDTO(List.of(owAirPollutionItemDTOList));
        // OpenMeteo Air Quality DTO
        OpenMeteoAirQualityHourlyDTO omAirQualityHourlyDTO = new OpenMeteoAirQualityHourlyDTO(
                List.of(new Long[]{0L, 1L}),
                List.of(new Double[]{1.0, 10.0}),
                List.of(new Double[]{2.0, 20.0}),
                List.of(new Double[]{3.0, 30.0}),
                List.of(new Double[]{4.0, 40.0}),
                List.of(new Double[]{5.0, 50.0}),
                List.of(new Double[]{6.0, 60.0}),
                List.of(new Double[]{7.0, 70.0})
        );
        omAirQualityDTO = new OpenMeteoAirQualityDTO(omAirQualityHourlyDTO);
    }

    @Test
    void givenOWGeocodingDTOAndOWAirPollutionDTO_whenEnvDTOfrom_thenValidEnvDTO() {
        OpenWeatherGeocodingDTO geocoding = owGeocodingDTO;
        OpenWeatherAirPollutionDTO airPollution = owAirPollutionDTO;

        EnvDTO env = ConverterUtils.envDTOfrom(geocoding, airPollution);

        assertThat(env.getLocation()).isEqualTo(geocoding.getLocation());
        assertThat(env.getCountry()).isEqualTo(geocoding.getCountry());
        for (int i = 0; i < airPollution.getList().size(); i++) {
            OpenWeatherAirPollutionItemDTO airPollutionItem = airPollution.getList().get(i);
            EnvItemDTO envItem = env.getItems().get(i);
            assertThat(envItem.getDt()).isEqualTo(1000 * airPollutionItem.getDt());
            assertThat(envItem.getComponents().getCo()).isEqualTo(airPollutionItem.getComponents().getCo());
            assertThat(envItem.getComponents().getNh3()).isEqualTo(airPollutionItem.getComponents().getNh3());
            assertThat(envItem.getComponents().getNo()).isEqualTo(airPollutionItem.getComponents().getNo());
            assertThat(envItem.getComponents().getNo2()).isEqualTo(airPollutionItem.getComponents().getNo2());
            assertThat(envItem.getComponents().getO3()).isEqualTo(airPollutionItem.getComponents().getO3());
            assertThat(envItem.getComponents().getPm10()).isEqualTo(airPollutionItem.getComponents().getPm10());
            assertThat(envItem.getComponents().getPm2_5()).isEqualTo(airPollutionItem.getComponents().getPm2_5());
            assertThat(envItem.getComponents().getSo2()).isEqualTo(airPollutionItem.getComponents().getSo2());
        }
    }

    @Test
    void givenOWGeocodingDTOAndOMAirQualityDTO_whenEnvDTOfrom_thenValidEnvDTO() {
        OpenWeatherGeocodingDTO geocoding = owGeocodingDTO;
        OpenMeteoAirQualityDTO airQuality = omAirQualityDTO;

        EnvDTO env = ConverterUtils.envDTOfrom(geocoding, airQuality);

        assertThat(env.getLocation()).isEqualTo(geocoding.getLocation());
        assertThat(env.getCountry()).isEqualTo(geocoding.getCountry());
        for (int i = 0; i < airQuality.getHourly().getTime().size(); i++) {
            EnvItemDTO envItem = env.getItems().get(i);
            assertThat(envItem.getDt()).isEqualTo(1000 * airQuality.getHourly().getTime().get(i));
            assertThat(envItem.getComponents().getCo()).isEqualTo(airQuality.getHourly().getCarbon_monoxide().get(i));
            assertThat(envItem.getComponents().getNh3()).isEqualTo(airQuality.getHourly().getAmmonia().get(i));
            assertThat(envItem.getComponents().getNo2()).isEqualTo(airQuality.getHourly().getNitrogen_dioxide().get(i));
            assertThat(envItem.getComponents().getO3()).isEqualTo(airQuality.getHourly().getOzone().get(i));
            assertThat(envItem.getComponents().getPm10()).isEqualTo(airQuality.getHourly().getPm10().get(i));
            assertThat(envItem.getComponents().getPm2_5()).isEqualTo(airQuality.getHourly().getPm2_5().get(i));
            assertThat(envItem.getComponents().getSo2()).isEqualTo(airQuality.getHourly().getSulphur_dioxide().get(i));
        }
    }

    @Test
    void givenEnvDTOWithOneItem_whenCurrentEnvDTOfrom_thenSameEnvDTO() {
        EnvItemDTO envItem = new EnvItemDTO(0L, 5, new EnvComponentsDTO());
        EnvDTO env = new EnvDTO("Aveiro", "PT", List.of(envItem));

        EnvDTO currentEnv = ConverterUtils.currentEnvDTOfrom(env);

        assertThat(currentEnv).isEqualTo(env);
    }

    @Test
    void givenCurrentTimeAndEnvDTOWithManyItems_whenCurrentEnvDTOfrom_thenCurrentEnvDTO() {
        long currentTime = System.currentTimeMillis();
        EnvItemDTO envItem1 = new EnvItemDTO(currentTime - 2000L, 5, new EnvComponentsDTO());
        EnvItemDTO envItem2 = new EnvItemDTO(currentTime - 1000L, 5, new EnvComponentsDTO());
        EnvItemDTO envItem3 = new EnvItemDTO(currentTime + 1000L, 5, new EnvComponentsDTO());
        EnvItemDTO envItem4 = new EnvItemDTO(currentTime + 2000L, 5, new EnvComponentsDTO());
        EnvDTO env = new EnvDTO("Aveiro", "PT", new ArrayList<>(List.of(envItem1, envItem2, envItem3, envItem4)));

        EnvDTO currentEnv = ConverterUtils.currentEnvDTOfrom(env);

        assertThat(currentEnv.getItems())
                .hasSize(1)
                .extracting(EnvItemDTO::getDt)
                .containsExactly(currentTime - 1000L);
    }

    @Test
    void givenCurrentTimeAndEnvDTOWithManyItemsInThePast_whenCurrentEnvDTOfrom_thenLastEnvDTO() {
        long currentTime = System.currentTimeMillis();
        EnvItemDTO envItem1 = new EnvItemDTO(currentTime - 4000L, 5, new EnvComponentsDTO());
        EnvItemDTO envItem2 = new EnvItemDTO(currentTime - 3000L, 5, new EnvComponentsDTO());
        EnvItemDTO envItem3 = new EnvItemDTO(currentTime - 2000L, 5, new EnvComponentsDTO());
        EnvItemDTO envItem4 = new EnvItemDTO(currentTime - 1000L, 5, new EnvComponentsDTO());
        EnvDTO env = new EnvDTO("Aveiro", "PT", new ArrayList<>(List.of(envItem1, envItem2, envItem3, envItem4)));

        EnvDTO currentEnv = ConverterUtils.currentEnvDTOfrom(env);

        assertThat(currentEnv.getItems())
                .hasSize(1)
                .extracting(EnvItemDTO::getDt)
                .containsExactly(currentTime - 1000L);
    }

    @Test
    void givenCache_whenCacheStatsDTOfrom_thenValidCacheStatsDTO() {
        IntegerCache cache = new IntegerCache(1000L, 5);

        CacheStatsDTO cacheStats = ConverterUtils.cacheStatsDTOfrom(cache);

        assertThat(cacheStats.getTtl()).isEqualTo(1000L);
        assertThat(cacheStats.getCapacity()).isEqualTo(5);
        assertThat(cacheStats.getNRequests()).isEqualTo(0);
        assertThat(cacheStats.getNHits()).isEqualTo(0);
        assertThat(cacheStats.getNMisses()).isEqualTo(0);
        assertThat(cacheStats.getNItems()).isEqualTo(0);
    }
}
