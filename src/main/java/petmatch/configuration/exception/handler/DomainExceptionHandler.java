package petmatch.configuration.exception.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import petmatch.configuration.exception.JwtTokenException;
import petmatch.configuration.exception.api.ApiError;

@RestControllerAdvice
@Log4j2
public class DomainExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handle JWT authentication cause by unauthorized user
     *
     * @param exception {@link  JwtTokenException}
     * @return {@link ResponseEntity<Object>}
     * @author Vien Binh
     */
    @ExceptionHandler(JwtTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleJwtTokenException(JwtTokenException exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .message("JWT Unauthorized: " + exception.getMessage())
                        .build());
    }

    /**
     * Wrong login information
     *
     * @param exception {@code AuthenticationException}
     * @return {@link ResponseEntity<Object>}
     * @author Vien Binh
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(Exception exception) {
        log.error(exception.getMessage());
        return buildResponseEntity(
                ApiError.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .message("Authentication failed. Wrong credentials: " + exception.getMessage())
                        .build());
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}
