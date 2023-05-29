package petmatch.api.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchRequest {
    private UUID matchFromProfileId; // my profile
    private UUID matchToProfileId;
}
