package tqs.hw1.envmonitor.util;

import tqs.hw1.envmonitor.data.env.EnvComponentsDTO;

public class EnvUtils {

    /*
     * Based on the World Health Organization's Air Quality Index (AQI) scale.
     * https://qualar.apambiente.pt/node/metodo-calculo-indices
     * Considered components: PM10, PM2.5, NO2, O3, SO2.
     * Considered AQI: worst among all components.
     * Scale:
     * 1 - Awful
     * 2 - Poor
     * 3 - Moderate
     * 4 - Good
     * 5 - Excellent
     */
    public static Integer getAqi(EnvComponentsDTO comp) {
        // Awful
        if (comp.getPm10() > 100
         || comp.getPm2_5() > 50
         || comp.getNo2() > 400
         || comp.getO3() > 240
         || comp.getSo2() > 500
        ) {
            return 1;
        }
        // Poor
        else if (
            comp.getPm10() > 50
         || comp.getPm2_5() > 25
         || comp.getNo2() > 200
         || comp.getO3() > 180
         || comp.getSo2() > 350
        ) {
            return 2;
        }
        // Moderate
        else if (
            comp.getPm10() > 35
         || comp.getPm2_5() > 20
         || comp.getNo2() > 100
         || comp.getO3() > 100
         || comp.getSo2() > 200
        ) {
            return 3;
        }
        // Good
        else if (
            comp.getPm10() > 20
         || comp.getPm2_5() > 10
         || comp.getNo2() > 40
         || comp.getO3() > 80
         || comp.getSo2() > 100
        ) {
            return 4;
        }
        // Excellent
        else {
            return 5;
        }
    }
}
