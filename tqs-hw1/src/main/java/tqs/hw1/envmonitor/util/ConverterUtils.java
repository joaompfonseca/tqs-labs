package tqs.hw1.envmonitor.util;

import tqs.hw1.envmonitor.cache.Cache;
import tqs.hw1.envmonitor.data.cache.CacheStatsDTO;
import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.env.EnvItemDTO;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionItemDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherGeocodingDTO;

import java.util.ArrayList;
import java.util.List;

public class ConverterUtils {
    public static EnvDTO envDTOfrom(OpenWeatherGeocodingDTO geoData, OpenWeatherAirPollutionDTO envData) {
        EnvDTO env = new EnvDTO();
        env.setLocation(geoData.getLocation());
        env.setCountry(geoData.getCountry());
        List<EnvItemDTO> envItems = new ArrayList<>();
        for (OpenWeatherAirPollutionItemDTO item : envData.getList()) {
            EnvItemDTO envItem = new EnvItemDTO();
            envItem.setDt(1000 * item.getDt());
            EnvComponentsDTO envComponents = new EnvComponentsDTO();
            envComponents.setCo(item.getComponents().getCo());
            envComponents.setNh3(item.getComponents().getNh3());
            envComponents.setNo(item.getComponents().getNo());
            envComponents.setNo2(item.getComponents().getNo2());
            envComponents.setO3(item.getComponents().getO3());
            envComponents.setPm10(item.getComponents().getPm10());
            envComponents.setPm2_5(item.getComponents().getPm2_5());
            envComponents.setSo2(item.getComponents().getSo2());
            envItem.setComponents(envComponents);
            envItems.add(envItem);
            envItem.setAqi(EnvUtils.getAqi(envComponents));
        }
        env.setItems(envItems);
        return env;
    }

    public static EnvDTO envDTOfrom(OpenWeatherGeocodingDTO geoData, OpenMeteoAirQualityDTO envData) {
        EnvDTO env = new EnvDTO();
        env.setLocation(geoData.getLocation());
        env.setCountry(geoData.getCountry());
        List<EnvItemDTO> envItems = new ArrayList<>();
        for (int i = 0; i < envData.getHourly().getTime().size(); i++) {
            EnvItemDTO envItem = new EnvItemDTO();
            envItem.setDt(1000 * envData.getHourly().getTime().get(i));
            EnvComponentsDTO envComponents = new EnvComponentsDTO();
            envComponents.setCo(envData.getHourly().getCarbon_monoxide().get(i));
            envComponents.setNh3(envData.getHourly().getAmmonia().get(i));
            envComponents.setNo2(envData.getHourly().getNitrogen_dioxide().get(i));
            envComponents.setO3(envData.getHourly().getOzone().get(i));
            envComponents.setPm10(envData.getHourly().getPm10().get(i));
            envComponents.setPm2_5(envData.getHourly().getPm2_5().get(i));
            envComponents.setSo2(envData.getHourly().getSulphur_dioxide().get(i));
            envItem.setComponents(envComponents);
            envItems.add(envItem);
            envItem.setAqi(EnvUtils.getAqi(envComponents));
        }
        env.setItems(envItems);
        return env;
    }

    public static EnvDTO currentEnvDTOfrom(EnvDTO envData) {
        List<EnvItemDTO> envItems = envData.getItems();
        // There are no items or only one item
        if (envItems.size() <= 1) {
            return envData;
        }
        // There is more than one item
        for (int i = 1; i < envItems.size(); i++) {
            // If the next item is in the future, return the previous item
            if (envItems.get(i).getDt() > System.currentTimeMillis()) {
                envData.setItems(envItems.subList(i - 1, i));
                return envData;
            }
        }
        // If all items are in the past, return the last item
        envData.setItems(envItems.subList(envItems.size() - 1, envItems.size()));
        return envData;
    }

    public static <K, V> CacheStatsDTO cacheStatsDTOfrom(Cache<K, V> cache) {
        CacheStatsDTO stats = new CacheStatsDTO();
        stats.setTtl(cache.getTtl());
        stats.setCapacity(cache.getCapacity());
        stats.setNRequests(cache.getNRequests());
        stats.setNHits(cache.getNHits());
        stats.setNMisses(cache.getNMisses());
        stats.setNItems(cache.getNItems());
        return stats;
    }
}
