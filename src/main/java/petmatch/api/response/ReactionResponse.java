package petmatch.api.response;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionResponse {
    private String comment;
    private UUID profileId;
    private UUID createdBy;
    private Date createdTimestamp;
    private Date updatedTimestamp;
}
