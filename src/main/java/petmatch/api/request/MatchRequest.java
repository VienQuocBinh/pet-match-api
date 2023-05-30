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
public class MatchRequest {
    @NotNull
    @JsonProperty("match-from")
    private UUID matchFromProfileId; // my profile
    @NotNull
    @JsonProperty("match-to")
    private UUID matchToProfileId;
}
