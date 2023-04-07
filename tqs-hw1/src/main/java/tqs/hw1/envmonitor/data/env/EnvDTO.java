package tqs.hw1.envmonitor.data.env;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnvDTO {
    private String location;
    private String country;
    private List<EnvItemDTO> items;
}
