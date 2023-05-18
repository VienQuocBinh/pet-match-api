package petmatch.service.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import petmatch.api.response.UserResponse;

import java.util.Date;

/**
 * Firebase user management service
 */
@Service
@RequiredArgsConstructor
public class UserManagementService {
    public UserResponse getFirebaseUserById(String uid) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
        return UserResponse.builder()
                .id(userRecord.getUid())
                .email(userRecord.getEmail())
                .createdTimestamp(new Date(userRecord.getUserMetadata().getCreationTimestamp()))
                .phone(userRecord.getPhoneNumber())
                .build();
    }

    public UserResponse getFirebaseUserByEmail(String email) throws FirebaseAuthException {
        UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
        return UserResponse.builder()
                .id(userRecord.getUid())
                .email(userRecord.getEmail())
                .createdTimestamp(new Date(userRecord.getUserMetadata().getCreationTimestamp()))
                .phone(userRecord.getPhoneNumber())
                .build();
    }
}
