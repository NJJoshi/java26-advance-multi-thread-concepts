package com.nj.virtualthread.learning.sec09.security.threadlocal;

import com.nj.virtualthread.learning.sec09.security.SecurityContext;
import com.nj.virtualthread.learning.sec09.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ThreadLocal<SecurityContext> contextHolder = ThreadLocal.withInitial(() -> ANONYMOUS_CONTEXT);

    static void setContext(SecurityContext context) {
        contextHolder.set(context);
    }

    static void clearContext() {
        contextHolder.remove();
    }

    public static SecurityContext getContext() {
        return contextHolder.get();
    }
}
