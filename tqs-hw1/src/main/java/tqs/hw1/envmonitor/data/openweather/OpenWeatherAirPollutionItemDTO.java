package tqs.hw1.envmonitor.data.openweather;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OpenWeatherAirPollutionItemDTO {
    private Long dt;
    private OpenWeatherAirPollutionComponentsDTO components;
}
