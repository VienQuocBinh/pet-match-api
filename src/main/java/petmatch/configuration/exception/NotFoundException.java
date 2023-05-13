package petmatch.configuration.exception;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends Exception {
    public static final int HTTP_NOT_FOUND_CODE = 404;
    private int HttpCode;
    private String entity;
}
