package com.nj.virtualthread.learning.sec09;

import com.nj.virtualthread.learning.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.UUID;

public class InheritableThreadLocalDemo {
    private static final Logger log = LoggerFactory.getLogger(InheritableThreadLocalDemo.class);
    private static final ThreadLocal<String> sessionTokenHolder = new InheritableThreadLocal<>();

    static void main(String[] args) {

        authFilter(InheritableThreadLocalDemo::orderController);

        CommonUtils.sleep(Duration.ofSeconds(1));
    }

    // below code is just to demonstrate the workflow
    // WebFilter
    private static void authFilter(Runnable runnable){
        try{
            var token = authenticate();
            sessionTokenHolder.set(token);
            // request processing
            runnable.run();
        }finally {
            sessionTokenHolder.remove();
        }
    }

    // Security
    private static String authenticate(){
        var token = UUID.randomUUID().toString();
        log.info("token={}", token);
        return token;
    }

    // @Principal
    // POST /orders
    private static void orderController(){
        log.info("orderController: {}", sessionTokenHolder.get());
        orderService();
    }

    private static void orderService(){
        log.info("orderService: {}", sessionTokenHolder.get());
        Thread.ofVirtual().name("child-1").start(InheritableThreadLocalDemo::callProductService);
        Thread.ofVirtual().name("child-2").start(InheritableThreadLocalDemo::callInventoryService);
    }

    // This is a client to call product service
    private static void callProductService(){
        sessionTokenHolder.set("mytest");
        log.info("calling product-service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }

    // This is a client to call inventory service
    private static void callInventoryService(){
        sessionTokenHolder.set("mytest");
        log.info("calling inventory-service with header. Authorization: Bearer {}", sessionTokenHolder.get());
    }
}
