package tqs.hw1.envmonitor.data.cache;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CacheStatsDTO {
    private Long ttl;
    private Integer capacity;
    private Integer nRequests;
    private Integer nHits;
    private Integer nMisses;
    private Integer nItems;
}
