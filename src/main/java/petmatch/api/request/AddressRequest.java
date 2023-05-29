package petmatch.api.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String address;
    private Double latitude;
    private Double longitude;
    private String userId;
}
