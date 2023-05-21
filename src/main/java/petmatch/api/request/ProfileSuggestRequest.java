package petmatch.api.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSuggestRequest {
    private UUID profileId; // my profile
}
