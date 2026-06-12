package com.nj.virtualthread.learning.sec10;

import com.nj.virtualthread.learning.sec10.service.FlightPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

public class StructureTaskScopeAwaitAllDemo {
    private static final Logger LOG = LoggerFactory.getLogger(StructureTaskScopeAwaitAllDemo.class);

    static void main() {
        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.awaitAll())) {
            var subtask1 = scope.fork(FlightPriceService::getDeltaAirfare);
            var subtask2 = scope.fork(FlightPriceService::getFrontierAirfare);
            var subtask3 = scope.fork(FlightPriceService::getUnitedAirfare);

            scope.join();

            LOG.info("subtask1 state: {}", subtask1.state());
            LOG.info("subtask2 state: {}", subtask2.state());
            LOG.info("subtask3 state: {}", subtask3.state());

            LOG.info("subtask 1 result: {}", subtask1.get());
            LOG.info("subtask2 result: {}", subtask2.get());
            LOG.info("subtask3 result: {}", subtask3.exception().getMessage());



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
