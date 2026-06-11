package com.nj.virtualthread.learning.sec09;

import org.slf4j.Logger;

public class ScopedValueDemo {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ScopedValueDemo.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    static void main() {
        LOG.info("Inside ScopedValue");
        ScopedValue.where(SESSION_TOKEN, "session-1")
                .run(ScopedValueDemo::checkingBinding);
        LOG.info("Outside ScopedValue");
        checkingBinding();
    }

    private static void checkingBinding(){
        LOG.info("is Bound: {}", SESSION_TOKEN.isBound());
        LOG.info("Value: {}", SESSION_TOKEN.orElse("Dummy Token"));
    }
}
