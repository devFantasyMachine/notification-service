package cm.fantasymachine.notification.domain.handlers;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.ports.NotificationStore;
import cm.fantasymachine.notification.domain.ports.UserSessionRegistry;
import lombok.AllArgsConstructor;

import java.util.Set;


@AllArgsConstructor
public class NotificationListener {

    private UserSessionRegistry userSessionRegistry;
    private NotificationStore notificationStore;


    public void onNotification(final Notification notification) {

        Set<String> deviceIds = notification.getMissingDeviceIds();

        for (String deviceId : deviceIds) {

            userSessionRegistry.getUserSession(notification.getUserId(), notification.getTenantId(), deviceId)
                    .ifPresent(userSession -> {

                        try {
                            userSession.sendNotification(notification);
                            notification.addSuccessDeviceId(deviceId);
                        }
                        catch (Exception ignored){}
                    });
        }

        notificationStore.save(notification);
    }





}
