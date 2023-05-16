package petmatch.configuration.exception.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {
    private HttpStatus httpStatus;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss", timezone = "Asia/Bangkok")
    private Date timestamp = Date.from(Instant.now());
    private String message;
    private String debugMessage;
    private List<ApiErrorDetails> details;

    private ApiError() {
        timestamp = Date.from(Instant.now());
    }

    public ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, Throwable exception) {
        this();
        this.httpStatus = httpStatus;
        this.message = "Unknown error";
        this.debugMessage = exception.getLocalizedMessage();
    }

    public ApiError(HttpStatus httpStatus, String message, Throwable exception) {
        this();
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = exception.getLocalizedMessage();
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param constraintViolations set of constraint violations
     */
    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(this::addValidationError);
    }

    /**
     * Utility method of addValidationErrors(Set<ConstraintViolation<?>> constraintViolations)
     *
     * @param constraint set of constraint violations
     */
    private void addValidationError(ConstraintViolation<?> constraint) {
        this.addValidationError(
                constraint.getRootBeanClass().getSimpleName(),
                ((PathImpl) constraint.getPropertyPath()).getLeafNode().asString(),
                constraint.getInvalidValue(),
                constraint.getMessage());
    }

    /**
     * Add each constraint error to the error detail list
     *
     * @param object        name of the object which violated the constraint
     * @param field         name of the field which violated the constraint
     * @param rejectedValue value of the field which violated the constraint
     * @param message       message for the field which violated the constraint
     */
    private void addValidationError(String object, String field, Object rejectedValue, String message) {
        addErrorDetail(new ApiErrorDetails(object, field, rejectedValue, message));
    }

    public void addValidationGlobalErrors(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationObjectError);
    }

    /**
     * Utility method of addValidationObjectErrors(List<ObjectError> globalErrors)
     *
     * @param objectError objectError
     */
    private void addValidationObjectError(ObjectError objectError) {
        this.addValidationError(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }


    public void addValidationFieldErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationFieldError);
    }

    private void addValidationFieldError(FieldError fieldError) {
        this.addValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    private void addValidationError(String object, String message) {
        addErrorDetail(new ApiErrorDetails(object, message));
    }

    private void addErrorDetail(ApiErrorDetails errorDetails) {
        if (details == null) {
            details = new ArrayList<>();
        }
        details.add(errorDetails);
    }
}
