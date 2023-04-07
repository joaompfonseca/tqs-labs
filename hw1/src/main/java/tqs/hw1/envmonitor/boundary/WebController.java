package tqs.hw1.envmonitor.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tqs.hw1.envmonitor.cache.EnvCache;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.service.EnvService;
import tqs.hw1.envmonitor.util.ConverterUtils;

@Controller
public class WebController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EnvService envService;
    private final EnvCache cache;

    public WebController(EnvService envService) {
        this.envService = envService;
        this.cache = EnvCache.getInstance();
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.info("GET /");
        model.addAttribute("cache", ConverterUtils.cacheStatsDTOfrom(cache));
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "q") String location, Model model) {
        if (location == null) {
            logger.info("Fallback to GET /");
            return index(model);
        }
        String sanitizedLocation = location.replaceAll("[\n\r]", "_");
        logger.info("GET /search?q=" + sanitizedLocation);
        if (sanitizedLocation.isBlank()) {
            logger.info("Fallback to GET /");
            return index(model);
        }
        EnvDTO current = envService.getCurrentEnv(sanitizedLocation);
        EnvDTO forecast = envService.getForecastEnv(sanitizedLocation);
        String locationAndCountry =
                (current != null) ? current.getLocation() + ", " + current.getCountry()
              : (forecast != null) ? forecast.getLocation() + ", " + forecast.getCountry()
              : null;
        model.addAttribute("query", location);
        model.addAttribute("location_country", locationAndCountry);
        model.addAttribute("cache", ConverterUtils.cacheStatsDTOfrom(cache));
        model.addAttribute("env_current", current);
        model.addAttribute("env_forecast", forecast);
        return "index";
    }
}
