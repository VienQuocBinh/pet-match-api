package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import petmatch.configuration.constance.Role;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private String id; // From Firebase
    private String email;
    private String phone;
    private List<AddressResponse> addresses;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private Role role;
    private SubscriptionResponse subscription;
}
