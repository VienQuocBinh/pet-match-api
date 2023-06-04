package petmatch.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionRequest {
    private String comment;
    @JsonProperty("profile-id")
    private UUID profileId;
    @JsonProperty("created-by")
    private UUID createdBy;
}
