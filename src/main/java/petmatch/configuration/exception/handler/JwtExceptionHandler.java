package petmatch.configuration.exception.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import petmatch.configuration.exception.api.ApiError;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class JwtExceptionHandler extends RuntimeException {
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Object> handleJwtException(JwtException ex) {
        String errorMessage = "An error occurred while processing the JWT token.";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (ex instanceof ExpiredJwtException) {
            errorMessage += "The JWT token has expired.";
        } else if (ex instanceof UnsupportedJwtException) {
            errorMessage += "The JWT token is not supported.";
        } else if (ex instanceof MalformedJwtException) {
            errorMessage += "The JWT token is malformed.";
        } else if (ex instanceof SignatureException) {
            errorMessage += "The JWT token has an invalid signature.";
        }
        return buildResponseEntity(ApiError.builder()
                .httpStatus(status)
                .message(errorMessage)
                .build());
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getHttpStatus());
    }
}

