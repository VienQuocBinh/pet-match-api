package petmatch.api.response;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {
    private UUID id;
    private UUID matchFrom;
    private UUID matchTo;
    private Date createdTimestamp;
    private Date updatedTimestamp;
}
