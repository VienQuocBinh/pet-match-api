package petmatch.configuration.exception.api;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDetails {
    private String object;
    private String field;
    private Object value;
    private String message;

    public ApiErrorDetails(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
