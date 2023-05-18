package petmatch.service.firebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import petmatch.configuration.exception.InternalServerErrorException;
import petmatch.service.UserService;

@Component
@RequiredArgsConstructor
public class TokenVerifier {
    private final UserManagementService userManagementService;
    private final UserService userService;

    /**
     * Verify id token from Firebase
     *
     * @param idTokenString {@code String}
     * @return {@code GoogleIdToken.Payload}
     * @author Vien Binh
     */
    public GoogleIdToken.Payload validate(String idTokenString) {
        try {
            GsonFactory gsonFactory = new GsonFactory();
            GoogleIdToken idToken = GoogleIdToken.parse(gsonFactory, idTokenString);
            userManagementService.getFirebaseUserByEmail(idToken.getPayload().getEmail()); // find in firebase
            return idToken.getPayload();
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getClass().getName() + ". " + e.getMessage());
        }
    }
}
