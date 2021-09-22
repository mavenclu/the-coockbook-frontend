package cz.mavenclu.cookbook.thymeleaf.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrincipalNotFoundException extends Exception {

    public PrincipalNotFoundException() {
        super();
        log.warn("PrincipalNotFoundException was thrown");
    }
}
