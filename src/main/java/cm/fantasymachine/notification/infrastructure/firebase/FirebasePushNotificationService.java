package cm.fantasymachine.notification.infrastructure.firebase;


import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.UserDevice;
import cm.fantasymachine.notification.domain.handlers.PushNotificationService;
import org.springframework.stereotype.Component;


@Component
public class FirebasePushNotificationService implements PushNotificationService {

    @Override
    public void send(Notification notification, UserDevice device) {
        PushNotificationService.super.send(notification, device);
    }

}
