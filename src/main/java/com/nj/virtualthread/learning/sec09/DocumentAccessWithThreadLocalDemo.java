package com.nj.virtualthread.learning.sec09;

import com.nj.virtualthread.learning.sec09.controller.DocumentController;
import com.nj.virtualthread.learning.sec09.security.threadlocal.AuthenticationService;
import com.nj.virtualthread.learning.sec09.security.threadlocal.SecurityContextHolder;
import com.nj.virtualthread.learning.util.CommonUtils;

import java.time.Duration;

public class DocumentAccessWithThreadLocalDemo {
    private static final DocumentController documentController = new DocumentController(SecurityContextHolder::getContext);

    static void main() {
        Thread.ofVirtual().name("admin").start(() -> documentAccessWorkflow(1, "password"));
        Thread.ofVirtual().name("editor").start(() -> documentAccessWorkflow(2, "password"));
        CommonUtils.sleep(Duration.ofSeconds(1));
    }

    private static void documentAccessWorkflow(Integer userId, String password) {
        AuthenticationService.loginAndExecute(userId, password, () -> {
            documentController.read();
            documentController.edit();
            documentController.delete();
        });
    }
}
