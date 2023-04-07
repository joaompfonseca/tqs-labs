package tqs.hw1.envmonitor.util;

import org.junit.jupiter.api.Test;
import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvUtilsTest {

    @Test
    void givenAwfulAirQuality_whenGetAqi_thenAqi1() {
        EnvComponentsDTO pm10 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 101.0, 0.0, 0.0);
        EnvComponentsDTO pm2_5 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 51.0, 0.0);
        EnvComponentsDTO no2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 401.0, 0.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO o3 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 241.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO so2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 501.0);

        assertThat(EnvUtils.getAqi(pm10)).isEqualTo(1);
        assertThat(EnvUtils.getAqi(pm2_5)).isEqualTo(1);
        assertThat(EnvUtils.getAqi(no2)).isEqualTo(1);
        assertThat(EnvUtils.getAqi(o3)).isEqualTo(1);
        assertThat(EnvUtils.getAqi(so2)).isEqualTo(1);
    }

    @Test
    void givenPoorAirQuality_whenGetAqi_thenAqi2() {
        EnvComponentsDTO pm10 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 51.0, 0.0, 0.0);
        EnvComponentsDTO pm2_5 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 26.0, 0.0);
        EnvComponentsDTO no2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 201.0, 0.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO o3 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 181.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO so2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 351.0);

        assertThat(EnvUtils.getAqi(pm10)).isEqualTo(2);
        assertThat(EnvUtils.getAqi(pm2_5)).isEqualTo(2);
        assertThat(EnvUtils.getAqi(no2)).isEqualTo(2);
        assertThat(EnvUtils.getAqi(o3)).isEqualTo(2);
        assertThat(EnvUtils.getAqi(so2)).isEqualTo(2);
    }

    @Test
    void givenModerateAirQuality_whenGetAqi_thenAqi3() {
        EnvComponentsDTO pm10 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 0.0, 0.0);
        EnvComponentsDTO pm2_5 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 21.0, 0.0);
        EnvComponentsDTO no2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 101.0, 0.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO o3 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 101.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO so2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 201.0);

        assertThat(EnvUtils.getAqi(pm10)).isEqualTo(3);
        assertThat(EnvUtils.getAqi(pm2_5)).isEqualTo(3);
        assertThat(EnvUtils.getAqi(no2)).isEqualTo(3);
        assertThat(EnvUtils.getAqi(o3)).isEqualTo(3);
        assertThat(EnvUtils.getAqi(so2)).isEqualTo(3);
    }

    @Test
    void givenGoodAirQuality_whenGetAqi_thenAqi4() {
        EnvComponentsDTO pm10 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 21.0, 0.0, 0.0);
        EnvComponentsDTO pm2_5 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 11.0, 0.0);
        EnvComponentsDTO no2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 41.0, 0.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO o3 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 81.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO so2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 101.0);

        assertThat(EnvUtils.getAqi(pm10)).isEqualTo(4);
        assertThat(EnvUtils.getAqi(pm2_5)).isEqualTo(4);
        assertThat(EnvUtils.getAqi(no2)).isEqualTo(4);
        assertThat(EnvUtils.getAqi(o3)).isEqualTo(4);
        assertThat(EnvUtils.getAqi(so2)).isEqualTo(4);
    }

    @Test
    void givenExcellentAirQuality_whenGetAqi_thenAqi5() {
        EnvComponentsDTO pm10 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 0.0, 0.0);
        EnvComponentsDTO pm2_5 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0);
        EnvComponentsDTO no2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO o3 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 80.0, 0.0, 0.0, 0.0);
        EnvComponentsDTO so2 = new EnvComponentsDTO(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0);

        assertThat(EnvUtils.getAqi(pm10)).isEqualTo(5);
        assertThat(EnvUtils.getAqi(pm2_5)).isEqualTo(5);
        assertThat(EnvUtils.getAqi(no2)).isEqualTo(5);
        assertThat(EnvUtils.getAqi(o3)).isEqualTo(5);
        assertThat(EnvUtils.getAqi(so2)).isEqualTo(5);
    }
}
