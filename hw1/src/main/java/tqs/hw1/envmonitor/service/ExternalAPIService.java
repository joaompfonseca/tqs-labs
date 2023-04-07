package tqs.hw1.envmonitor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.data.openmeteo.OpenMeteoAirQualityDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherAirPollutionDTO;
import tqs.hw1.envmonitor.data.openweather.OpenWeatherGeocodingDTO;
import tqs.hw1.envmonitor.external.openmeteo.OpenMeteoAirQualityAPI;
import tqs.hw1.envmonitor.external.openweather.OpenWeatherAirPollutionAPI;
import tqs.hw1.envmonitor.external.openweather.OpenWeatherGeocodingAPI;
import tqs.hw1.envmonitor.util.ConverterUtils;

import java.util.List;

@Service
public class ExternalAPIService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OpenWeatherGeocodingAPI openWeatherGeocodingAPI;
    private final OpenWeatherAirPollutionAPI openWeatherAirPollutionAPI;
    private final OpenMeteoAirQualityAPI openMeteoAirQualityAPI;

    public ExternalAPIService(OpenWeatherGeocodingAPI openWeatherGeocodingAPI, OpenWeatherAirPollutionAPI openWeatherAirPollutionAPI, OpenMeteoAirQualityAPI openMeteoAirQualityAPI) {
        this.openWeatherGeocodingAPI = openWeatherGeocodingAPI;
        this.openWeatherAirPollutionAPI = openWeatherAirPollutionAPI;
        this.openMeteoAirQualityAPI = openMeteoAirQualityAPI;
    }

    public EnvDTO getCurrentEnv(String location) {
        // Get coordinates from OpenWeather Geocoding API
        List<OpenWeatherGeocodingDTO> geoDataList = openWeatherGeocodingAPI.getCoordinates(location);
        if (geoDataList.isEmpty()) {
            logger.warn("Current: No coordinates found for location \"" + location + "\"");
            return null;
        }
        OpenWeatherGeocodingDTO geoData = geoDataList.get(0);
        logger.info("Current: Coordinates found for location \"" + location + "\": (lat=" + geoData.getLat() + ", lon=" + geoData.getLon() + ")");

        // Try to get from OpenWeather Air Pollution API
        try {
            OpenWeatherAirPollutionDTO envData = openWeatherAirPollutionAPI.getCurrent(geoData.getLat(), geoData.getLon());
            EnvDTO res = ConverterUtils.envDTOfrom(geoData, envData);
            logger.info("Current: Got " + res.getItems().size() + " data items from OpenWeather Air Pollution API");
            return res;
        } catch (Exception e) {
            // Next API
        }
        // Try to get from OpenMeteo Air Quality API
        try {
            OpenMeteoAirQualityDTO envData = openMeteoAirQualityAPI.getForecast(geoData.getLat(), geoData.getLon());
            EnvDTO res = ConverterUtils.currentEnvDTOfrom(ConverterUtils.envDTOfrom(geoData, envData));
            logger.info("Current: Got " + res.getItems().size() + " data items from OpenMeteo Air Quality API");
            return res;
        } catch (Exception e) {
            // Return null
        }
        logger.warn("Current: No environment data found for location \"" + location + "\"");
        return null;
    }

    public EnvDTO getForecastEnv(String location) {
        // Get coordinates from OpenWeather Geocoding API
        List<OpenWeatherGeocodingDTO> geoDataList = openWeatherGeocodingAPI.getCoordinates(location);
        if (geoDataList.isEmpty()) {
            logger.warn("Forecast: No coordinates found for location \"" + location + "\"");
            return null;
        }
        OpenWeatherGeocodingDTO geoData = geoDataList.get(0);
        logger.info("Forecast: Coordinates found for location \"" + location + "\": (lat=" + geoData.getLat() + ", lon=" + geoData.getLon() + ")");

        // Try to get from OpenWeather Air Pollution API
        try {
            OpenWeatherAirPollutionDTO envData = openWeatherAirPollutionAPI.getForecast(geoData.getLat(), geoData.getLon());
            EnvDTO res = ConverterUtils.envDTOfrom(geoData, envData);
            logger.info("Forecast: Got " + res.getItems().size() + " data items from OpenWeather Air Pollution API");
            return res;
        } catch (Exception e) {
            // Next API
        }
        // Try to get from OpenMeteo Air Quality API
        try {
            OpenMeteoAirQualityDTO envData = openMeteoAirQualityAPI.getForecast(geoData.getLat(), geoData.getLon());
            EnvDTO res = ConverterUtils.envDTOfrom(geoData, envData);
            logger.info("Forecast: Got " + res.getItems().size() + " data items from OpenMeteo Air Quality API");
            return res;
        } catch (Exception e) {
            // Return null
        }
        logger.warn("Forecast: No environment data found for location \"" + location + "\"");
        return null;
    }
}
