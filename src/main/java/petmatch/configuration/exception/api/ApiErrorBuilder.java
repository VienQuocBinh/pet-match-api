package petmatch.configuration.exception.api;

import org.springframework.http.ResponseEntity;
import petmatch.configuration.exception.DataNotFoundException;

import java.util.List;

public class ApiErrorBuilder {
    public static ResponseEntity<ApiError> buildApiErrorResponse(Exception e, Status status) {
        return new ResponseEntity<>(
                ApiError.builder()
                        .errorId(status.getErrorId())
                        .message(status.getMessage())
                        .httpStatus(status.getStatus())
                        .details(buildApiErrorDetails(e, status))
                        .build(), status.getStatus()
        );
    }

    private static List<ApiErrorDetails> buildApiErrorDetails(Exception exception, Status status) {
        if (exception instanceof DataNotFoundException) {
            return buildDetails((DataNotFoundException) exception);
        }
        return buildDetails(status);
    }

    private static List<ApiErrorDetails> buildDetails(Status status) {
        return List.of(ApiErrorDetails.builder()
                .issue(status.getIssue()).build());
    }

    private static List<ApiErrorDetails> buildDetails(DataNotFoundException exception) {
        return List.of(ApiErrorDetails.builder()
                .field(exception.getField())
                .issue(exception.getMessage())
                .build());
    }

}
