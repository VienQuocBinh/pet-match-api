package petmatch.configuration.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import petmatch.configuration.exception.api.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataNotFoundException extends EntityNotFoundException {
    private String field;
    private String message;

    private static final String NOT_FOUND_MSG = Status.NOT_FOUND.getMessage();
    public DataNotFoundException(String field) { this(field, NOT_FOUND_MSG); }

}
