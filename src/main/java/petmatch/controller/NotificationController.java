package petmatch.controller;

import com.google.firebase.messaging.BatchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import petmatch.api.domain.Notification;
import petmatch.service.NotificationService;

//@RestController
//@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    //    @PostMapping
    public BatchResponse sendNotification(@RequestBody Notification notification) {
        return notificationService.sendNotification(notification);
    }
}
