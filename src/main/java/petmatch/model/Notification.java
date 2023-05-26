package petmatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {
    private String subject; // Subject notification on firebase
    private String content; // Content notification on firebase
    private String imageUrl;
    private Map<String, String> data; // Additional data
    private List<String> registrationTokens; // FCM registration token
}
