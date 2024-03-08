package cm.fantasymachine.notification.domain.handlers;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.UserDevice;





public interface PushNotificationService {

    default void send(Notification notification, UserDevice device) {
        throw new UnsupportedOperationException();
    }

}
