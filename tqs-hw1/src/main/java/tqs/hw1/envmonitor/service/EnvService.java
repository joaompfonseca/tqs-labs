package tqs.hw1.envmonitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tqs.hw1.envmonitor.cache.EnvCache;
import tqs.hw1.envmonitor.data.env.EnvDTO;

@Service
public class EnvService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ExternalAPIService externalAPIService;
    private final EnvCache cache;

    public EnvService(ExternalAPIService externalAPIService) {
        this.externalAPIService = externalAPIService;
        this.cache = EnvCache.getInstance();
    }

    public EnvDTO getCurrentEnv(String location) {
        EnvDTO res = cache.get("current:" + location);
        if (res == null) {
            logger.info("Current: Cache miss for location \"" + location + "\"");
            res = externalAPIService.getCurrentEnv(location);
            cache.put("current:" + location, res);
            logger.info("Current: Added location \"" + location + "\" to cache");
        } else {
            logger.info("Current: Cache hit for location \"" + location + "\"");
        }
        return res;
    }

    public EnvDTO getForecastEnv(String location) {
        EnvDTO res = cache.get("forecast:" + location);
        if (res == null) {
            logger.info("Forecast: Cache miss for location \"" + location + "\"");
            res = externalAPIService.getForecastEnv(location);
            cache.put("forecast:" + location, res);
            logger.info("Forecast: Added location \"" + location + "\" to cache");
        } else {
            logger.info("Forecast: Cache hit for location \"" + location + "\"");
        }
        return res;
    }

}
