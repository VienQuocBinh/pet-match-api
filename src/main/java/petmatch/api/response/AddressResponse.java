package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {
    private UUID id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String geoHash;
    private String profileId;
}
