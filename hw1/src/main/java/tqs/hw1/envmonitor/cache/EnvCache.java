package tqs.hw1.envmonitor.cache;

import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.util.ConfigUtils;

public class EnvCache extends Cache<String, EnvDTO> {
    private static EnvCache instance;

    private EnvCache(Long ttl, Integer capacity) {
        super(ttl, capacity);
    }

    public static EnvCache getInstance() {
        if (instance == null) {
            instance = new EnvCache(
                    Long.parseLong(ConfigUtils.getProperty("cache.ttl")),
                    Integer.parseInt(ConfigUtils.getProperty("cache.capacity"))
            );
        }
        return instance;
    }
}
