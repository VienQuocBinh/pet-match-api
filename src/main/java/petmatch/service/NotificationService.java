package petmatch.service;

import com.google.firebase.messaging.BatchResponse;
import petmatch.api.domain.Notification;
import petmatch.model.Profile;

public interface NotificationService {
    BatchResponse sendNotification(Notification notice);

    /**
     * Send notification to target profile with custom content
     *
     * @param profile {@code Profile}
     * @param content {@code String}
     */
    void sendNotification(Profile profile, String content);
}
