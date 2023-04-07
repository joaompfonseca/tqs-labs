package tqs.hw1.envmonitor.external.openmeteo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityDTO;

@FeignClient(name = "${openmeteo.air_quality.name}", url = "${openmeteo.air_quality.url}")
public interface OpenMeteoAirQualityAPI {

    @GetMapping("?latitude={lat}&longitude={lon}&timeformat=unixtime&hourly=pm10,pm2_5,carbon_monoxide,nitrogen_dioxide,sulphur_dioxide,ozone,ammonia")
    OpenMeteoAirQualityDTO getForecast(@PathVariable Double lat, @PathVariable Double lon);
}
