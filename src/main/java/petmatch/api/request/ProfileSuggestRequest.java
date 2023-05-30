package petmatch.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSuggestRequest {
    @JsonProperty("profile-id")
    private UUID profileId; // my profile
}
