package petmatch.api.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private UUID id;
    private String address;
    private Double latitude;
    private Double longitude;
    private String geoHash;
    private String profileId;
}
