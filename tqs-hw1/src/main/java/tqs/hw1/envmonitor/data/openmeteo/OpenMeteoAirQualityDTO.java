package tqs.hw1.envmonitor.data.openmeteo;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OpenMeteoAirQualityDTO {
    private OpenMeteoAirQualityHourlyDTO hourly;
}
