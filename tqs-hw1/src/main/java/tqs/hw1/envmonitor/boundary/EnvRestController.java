package tqs.hw1.envmonitor.boundary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tqs.hw1.envmonitor.data.env.EnvDTO;
import tqs.hw1.envmonitor.service.EnvService;

@RestController
@RequestMapping("/api/env")
public class EnvRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final EnvService envService;

    public EnvRestController(EnvService envService) {
        this.envService = envService;
    }

    @GetMapping("/current")
    public EnvDTO getCurrentEnv(@RequestParam("q") String location) {
        String sanitizedLocation = location.replaceAll("[\n\r]", "_");
        logger.info("GET /api/env/current?q=" + sanitizedLocation);
        EnvDTO current = envService.getCurrentEnv(sanitizedLocation);
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return current;
        }
    }

    @GetMapping("/forecast")
    public EnvDTO getForecastEnv(@RequestParam("q") String location) {
        String sanitizedLocation = location.replaceAll("[\n\r]", "_");
        logger.info("GET /api/env/forecast?q=" + sanitizedLocation);
        EnvDTO forecast = envService.getForecastEnv(sanitizedLocation);
        if (forecast == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            return forecast;
        }
    }
}
