package petmatch.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    //    private String email;
    //    private String password;
    @NotNull
    private String idTokenString;
    @NotNull
    private String fcmToken;
}
