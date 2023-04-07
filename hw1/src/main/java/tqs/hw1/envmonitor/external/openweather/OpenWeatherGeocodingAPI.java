package tqs.hw1.envmonitor.external.openweather;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherGeocodingDTO;

import java.util.List;

@FeignClient(name = "${openweather.geocoding.name}", url = "${openweather.geocoding.url}")
public interface OpenWeatherGeocodingAPI {
    @GetMapping("/direct?q={location}&appid=${openweather.api_key}")
    List<OpenWeatherGeocodingDTO> getCoordinates(@PathVariable String location);

    @GetMapping("/reverse?lat={lat}&lon={lon}&appid=${openweather.api_key}")
    List<OpenWeatherGeocodingDTO> getLocation(@PathVariable Double lat, @PathVariable Double lon);
}
