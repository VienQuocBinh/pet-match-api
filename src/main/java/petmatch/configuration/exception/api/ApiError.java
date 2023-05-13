package petmatch.configuration.exception.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {
    private String errorId;
    private String message;
    private List<ApiErrorDetails> details;
    private HttpStatus httpStatus;
    private Date timestamp;
}
