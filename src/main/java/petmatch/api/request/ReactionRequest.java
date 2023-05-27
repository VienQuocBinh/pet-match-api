package petmatch.api.request;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionRequest {
    private String comment;
    private UUID profileId;
    private UUID createdBy;
}
