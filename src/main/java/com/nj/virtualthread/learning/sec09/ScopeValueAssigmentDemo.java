package com.nj.virtualthread.learning.sec09;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ScopeValueAssigmentDemo {
    private static final Logger logger = LoggerFactory.getLogger(ScopeValueAssigmentDemo.class);
    private static final ScopedValue<String> sessionValue = ScopedValue.newInstance();

    static void main() throws ExecutionException, InterruptedException {
        authFilter(ScopeValueAssigmentDemo::orderController);
        authFilter(ScopeValueAssigmentDemo::orderController);
        Thread.ofVirtual().name("request-",1).start(() -> authFilter(ScopeValueAssigmentDemo::orderController));
        Thread.ofVirtual().name("request-",2).start(() -> authFilter(ScopeValueAssigmentDemo::orderController));
        CommonUtils.sleep(Duration.ofSeconds(5));
    }

    private static void authFilter(Runnable runnable){
            var token = authenticate();
            ScopedValue.where(sessionValue, token).run(runnable);

    }

    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        logger.info("Authenticated with token: {}", token);
        return token;
    }

    private static void orderController(){
        logger.info("Order controller : {}", sessionValue.get() );
        orderService();
    }

    private static void orderService(){
        logger.info("Order service : {}", sessionValue.get() );
        callProductService();
        callInventoryService();
    }

    // This is a client to call product service
    private static void callProductService(){
        logger.info("calling product-service with header. Authorization: Bearer {}", sessionValue.get());
    }

    // This is a client to call inventory service
    private static void callInventoryService(){
        logger.info("calling inventory-service with header. Authorization: Bearer {}", sessionValue.get());
    }
}
