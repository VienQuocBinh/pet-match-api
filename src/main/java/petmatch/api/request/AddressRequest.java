package petmatch.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    @NotNull
    private String address;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @NotNull
    private UUID profileId;
}
