package petmatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import petmatch.api.ApiError;
import petmatch.configuration.exception.NotFoundException;

import static petmatch.configuration.exception.NotFoundException.HTTP_NOT_FOUND_CODE;

@RestControllerAdvice
public class ExceptionHandlerController {

    private static final int HTTP_INTERNAL_ERR_CODE = 500;
    private static final String NOT_FOUND_ERROR = "NOT FOUND";
    private static final String NOT_FOUND_MSG = " not found";
    private static final String UNKNOWN_ERROR = "500 INTERNAL SERVER ERROR";
    private static final String UNKOWN_MSG = "System unavailable";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException e) {
        var res = ApiError.builder().error(NOT_FOUND_ERROR).message(e.getEntity() + NOT_FOUND_MSG).build();
        return ResponseEntity.status(HTTP_NOT_FOUND_CODE).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> unknownError(Exception e) {
        var res = ApiError.builder().error(UNKNOWN_ERROR).message(UNKOWN_MSG).build();
        return ResponseEntity.status(HTTP_INTERNAL_ERR_CODE).body(res);
    }
}
