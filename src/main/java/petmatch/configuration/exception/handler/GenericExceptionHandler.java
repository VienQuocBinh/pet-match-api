package petmatch.configuration.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import petmatch.configuration.exception.api.ApiError;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class GenericExceptionHandler extends RuntimeException {
    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param exception exception
     * @return the ApiError object
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception) {
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR,
                "Not handle: " + exception.getClass().getSimpleName(),
                exception);
        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }
}
