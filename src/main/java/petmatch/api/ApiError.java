package petmatch.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class ApiError {
    private String error;
    private String message;
    private Date timestamp;
}
