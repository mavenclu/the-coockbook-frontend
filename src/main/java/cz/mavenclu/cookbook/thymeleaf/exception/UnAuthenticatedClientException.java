package cz.mavenclu.cookbook.thymeleaf.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnAuthenticatedClientException extends Exception {

    public UnAuthenticatedClientException() {
        super();
        log.warn("Client is not authenticated.");
    }
}
