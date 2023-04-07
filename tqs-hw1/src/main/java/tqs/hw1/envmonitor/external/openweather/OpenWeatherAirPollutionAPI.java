package tqs.hw1.envmonitor.external.openweather;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionDTO;

@FeignClient(name = "${openweather.air_polution.name}", url = "${openweather.air_polution.url}")
public interface OpenWeatherAirPollutionAPI {

    @GetMapping("?lat={lat}&lon={lon}&appid=${openweather.api_key}")
    OpenWeatherAirPollutionDTO getCurrent(@PathVariable Double lat, @PathVariable Double lon);

    @GetMapping("/forecast?lat={lat}&lon={lon}&appid=${openweather.api_key}")
    OpenWeatherAirPollutionDTO getForecast(@PathVariable Double lat, @PathVariable Double lon);
}
