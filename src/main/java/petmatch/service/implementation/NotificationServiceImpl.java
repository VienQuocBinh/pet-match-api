package petmatch.service.implementation;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import petmatch.model.Notification;
import petmatch.service.NotificationService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public BatchResponse sendNotification(Notification notice) {
        List<String> registrationTokens = notice.getRegistrationTokens();
        com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder()
                .setTitle(notice.getSubject())
                .setBody(notice.getContent())
                .setImage(notice.getImageUrl())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(notification)
                .putAllData(notice.getData())
                .build();

        BatchResponse batchResponse = null;
        try {
            batchResponse = FirebaseMessaging.getInstance().sendMulticast(message);
        } catch (FirebaseMessagingException e) {
            log.info("Firebase error {}", e.getMessage());
        }
        assert batchResponse != null : "Batch response is null";
        if (batchResponse.getFailureCount() > 0) {
            List<SendResponse> responses = batchResponse.getResponses();
            List<String> failedTokens = new ArrayList<>();
            for (int i = 0; i < responses.size(); i++) {
                if (!responses.get(i).isSuccessful()) {
                    failedTokens.add(registrationTokens.get(i));
                }
            }
            log.info("List of tokens that caused failures: " + failedTokens);
        }
        return batchResponse;
    }
}
