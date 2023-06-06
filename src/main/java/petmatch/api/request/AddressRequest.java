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
public class AddressRequest {
    @NotNull
    private String address;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @JsonProperty("profile-id")
    private UUID profileId;
}
