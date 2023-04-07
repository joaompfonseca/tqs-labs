package tqs.hw1.envmonitor.data.openweather;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OpenWeatherAirPollutionDTO {
    private List<OpenWeatherAirPollutionItemDTO> list;
}
