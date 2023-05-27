package petmatch.service;

import com.google.firebase.messaging.BatchResponse;
import petmatch.api.domain.Notification;

public interface NotificationService {
    BatchResponse sendNotification(Notification notice);
}
