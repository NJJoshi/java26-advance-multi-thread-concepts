package com.nj.virtualthread.learning.sec09.security.scopedvalue;

import com.nj.virtualthread.learning.sec09.security.SecurityContext;
import com.nj.virtualthread.learning.sec09.security.UserRole;

public class SecurityContextHolder {

    private static final SecurityContext ANONYMOUS_CONTEXT = new SecurityContext(0, UserRole.ANONYMOUS);
    private static final ScopedValue<SecurityContext> CONTEXT = ScopedValue.newInstance();

    public static ScopedValue<SecurityContext> getScopedValue() {
        return CONTEXT;
    }

    public static SecurityContext getContext() {
        return CONTEXT.orElse(ANONYMOUS_CONTEXT);
    }
}
