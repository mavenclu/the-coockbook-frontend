package cz.mavenclu.cookbook.thymeleaf.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterRecipesNotRetrievedException extends Exception {

    public FilterRecipesNotRetrievedException() {
        super();
        log.error("Failed to retrieve filtered recipes.");
    }
}
