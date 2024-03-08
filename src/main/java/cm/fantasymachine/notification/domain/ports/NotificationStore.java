package cm.fantasymachine.notification.domain.ports;


import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.NotificationPriority;

import java.util.Collection;
import java.util.Set;

public interface NotificationStore {

    default Notification save(Notification notification){
        throw new UnsupportedOperationException();
    }

    default void saveAll(Collection<Notification> notifications){
        throw new UnsupportedOperationException();
    }

    Set<Notification> getUnSendByUserIdTenantId(String userId, String tenantId, NotificationPriority priority);

}
