package tqs.hw1.envmonitor.data.env;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnvItemDTO {
    private Long dt;
    private Integer aqi;
    private EnvComponentsDTO components;
}
