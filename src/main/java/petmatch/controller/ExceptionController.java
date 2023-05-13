package petmatch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import petmatch.configuration.exception.DataNotFoundException;
import petmatch.configuration.exception.api.ApiError;
import petmatch.configuration.exception.api.ApiErrorBuilder;
import petmatch.configuration.exception.api.Status;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ApiError> handleDataNotFoundException(Exception exception) {
        logException(exception);
        return ApiErrorBuilder.buildApiErrorResponse(exception, Status.NOT_FOUND);
    }

    private void logException(Throwable exception) {
        log.error(String.format("Message=\"%s\", exception=", exception.getMessage()), exception);
    }
}
