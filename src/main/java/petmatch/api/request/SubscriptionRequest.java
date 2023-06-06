package petmatch.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequest {
    @NotNull
    private String name;
    @NotNull
    private String userId;
}
