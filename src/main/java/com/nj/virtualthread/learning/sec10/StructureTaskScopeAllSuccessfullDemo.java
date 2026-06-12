package com.nj.virtualthread.learning.sec10;

import com.nj.virtualthread.learning.sec10.service.FlightPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.StructuredTaskScope;

public class StructureTaskScopeAllSuccessfullDemo {
    private static final Logger LOG = LoggerFactory.getLogger(StructureTaskScopeAllSuccessfullDemo.class);

    static void main() {
        try(var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.allSuccessfulOrThrow())){

            // submit the subtasks
            var subtask1 = scope.fork(FlightPriceService::getDeltaAirfare);
            var subtask2 = scope.fork(FlightPriceService::getUnitedAirfare);

            // wait for all the subtasks to complete successfully
            scope.join();

            // check the state
            LOG.info("subtask1 state: {}", subtask1.state());
            LOG.info("subtask2 state: {}", subtask2.state());

            // get the result
            LOG.info("subtask1 result: {}", subtask1.get());
            LOG.info("subtask2 result: {}", subtask2.get());


        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
