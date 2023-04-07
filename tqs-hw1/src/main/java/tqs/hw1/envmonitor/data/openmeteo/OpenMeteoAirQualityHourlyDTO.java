package tqs.hw1.envmonitor.data.openmeteo;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OpenMeteoAirQualityHourlyDTO {
    private List<Long> time;
    private List<Double> ammonia;
    private List<Double> carbon_monoxide;
    private List<Double> nitrogen_dioxide;
    private List<Double> ozone;
    private List<Double> pm10;
    private List<Double> pm2_5;
    private List<Double> sulphur_dioxide;

}
