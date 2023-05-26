package petmatch.service;

import com.google.firebase.messaging.BatchResponse;
import petmatch.model.Notification;

public interface NotificationService {
    BatchResponse sendNotification(Notification notice);
}
