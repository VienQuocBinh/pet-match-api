package petmatch.configuration.exception.api;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDetails {
    private String field;
    private String value;
    private String location;
    private String issue;
}
