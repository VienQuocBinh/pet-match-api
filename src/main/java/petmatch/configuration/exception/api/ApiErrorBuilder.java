package petmatch.configuration.exception.api;

import org.springframework.http.ResponseEntity;
import petmatch.configuration.constance.ErrorStatus;
import petmatch.configuration.exception.EntityNotFoundException;

import java.util.List;

public class ApiErrorBuilder {
    public static ResponseEntity<ApiError> buildApiErrorResponse(Exception exception, ErrorStatus errorStatus) {
        return new ResponseEntity<>(
                ApiError.builder()
//                        .errorId(errorStatus.getErrorId())
                        .message(errorStatus.getMessage())
                        .httpStatus(errorStatus.getStatus())
                        .details(buildApiErrorDetails(exception, errorStatus))
                        .build(), errorStatus.getStatus()
        );
    }

    private static List<ApiErrorDetails> buildApiErrorDetails(Exception exception, ErrorStatus errorStatus) {
        if (exception instanceof EntityNotFoundException) {
            return buildDetails((EntityNotFoundException) exception);
        }
        return buildDetails(errorStatus);
    }

    private static List<ApiErrorDetails> buildDetails(ErrorStatus errorStatus) {
        return List.of(ApiErrorDetails.builder()
                .message(errorStatus.getIssue()).build());
    }

    private static List<ApiErrorDetails> buildDetails(EntityNotFoundException exception) {
        return List.of(ApiErrorDetails.builder()
//                .field(exception.getField())
                .message(exception.getMessage())
                .build());
    }

}
