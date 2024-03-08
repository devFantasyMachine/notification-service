package cm.fantasymachine.notification.domain.ports;

import cm.fantasymachine.notification.domain.core.Notification;

public interface NotificationEventProducer {
    default void publish(Notification notification) {
        throw new UnsupportedOperationException();
    }
}
