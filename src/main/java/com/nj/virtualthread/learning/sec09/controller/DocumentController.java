package com.nj.virtualthread.learning.sec09.controller;

import com.nj.virtualthread.learning.sec09.security.SecurityContext;
import com.nj.virtualthread.learning.sec09.security.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;
//@RestController
public class DocumentController {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);
    private final Supplier<SecurityContext> securityContextSupplier;

    public DocumentController(Supplier<SecurityContext> securityContextSupplier) {
        this.securityContextSupplier     = securityContextSupplier;
    }

    //@HasRole(VIEWER)
    public void read(){
        this.validateUserRole(UserRole.VIEWER);
        LOG.info("reading");
    }

    //@HasRole(EDITOR)
    public void edit(){
        this.validateUserRole(UserRole.EDITOR);
        LOG.info("editing");
    }

    //@HasRole(ADMIN)
    public void delete(){
        this.validateUserRole(UserRole.ADMIN);
        LOG.info("deleting");
    }

    private void validateUserRole(UserRole requiredRole){
        var securityContext = securityContextSupplier.get();
        if(!securityContext.hasPermission(requiredRole)){
            LOG.error("user {} does not have {} permission", securityContext.userId(), requiredRole);
            throw new SecurityException("Unauthorized access. Required role: " + requiredRole);
        }
    }
}
