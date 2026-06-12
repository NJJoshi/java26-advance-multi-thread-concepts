package com.nj.virtualthread.learning.sec10.service;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class FlightPriceService {
    private static final Logger LOG = LoggerFactory.getLogger(FlightPriceService.class);

    public static String getDeltaAirfare() {
        LOG.info("calling getDeltaAirfare");
        CommonUtils.sleep("delta", Duration.ofSeconds(1));
        return "Delta-$" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String getFrontierAirfare() {
        LOG.info("calling getFrontierAirfare");
        CommonUtils.sleep("frontier", Duration.ofSeconds(1));
        return "Delta-$" + ThreadLocalRandom.current().nextInt(100, 1000);
    }

    public static String getUnitedAirfare() {
        LOG.info("calling getUnitedAirfare");
       throw new RuntimeException("503: Service Unavailable");
    }
}
