package petmatch.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionRequest {
    private String comment;
    @NotNull
    @JsonProperty("profile-id")
    private UUID profileId;
    @NotNull
    @JsonProperty("created-by")
    private UUID createdBy;
}
