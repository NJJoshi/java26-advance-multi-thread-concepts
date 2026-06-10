package com.nj.virtualthread.learning.sec09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ThreadLocalDemo {
    private static final Logger  logger = LoggerFactory.getLogger(ThreadLocalDemo.class);
    private static final ThreadLocal<String> sessionHolder =  new ThreadLocal<>();

    static void main() {
        authFilter(ThreadLocalDemo::orderController);
        authFilter(ThreadLocalDemo::orderController);
    }

    private static void authFilter(Runnable runnable){
        try {
            var token = authenticate();
            sessionHolder.set(token);
            runnable.run();
        } finally {
            logger.info("Removing Session Holder value: " + sessionHolder.get());
            sessionHolder.remove();
        }
    }

    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        logger.info("Authenticated with token: {}", token);
        return token;
    }

    private static void orderController(){
        logger.info("Order controller : {}", sessionHolder.get() );
        orderService();
    }

    private static void orderService(){
        logger.info("Order service : {}", sessionHolder.get() );
        callProductService();
        callInventoryService();
    }

    // This is a client to call product service
    private static void callProductService(){
        logger.info("calling product-service with header. Authorization: Bearer {}", sessionHolder.get());
    }

    // This is a client to call inventory service
    private static void callInventoryService(){
        logger.info("calling inventory-service with header. Authorization: Bearer {}", sessionHolder.get());
    }
}
