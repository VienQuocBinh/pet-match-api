package petmatch.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import petmatch.configuration.constance.Role;
import petmatch.model.Subscription;

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
    //    private String password;
    private String phone;
    private List<AddressResponse> addresses;
    private Subscription subscription;
    //    private List<Profile> profiles;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private Role role;
}
