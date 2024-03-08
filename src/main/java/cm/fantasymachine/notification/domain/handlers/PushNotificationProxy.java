package cm.fantasymachine.notification.domain.handlers;


import cm.fantasymachine.notification.domain.core.DevicePlatform;
import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.UserDevice;
import lombok.AllArgsConstructor;

import java.util.Map;


@AllArgsConstructor
public class PushNotificationProxy implements PushNotificationService {


    private Map<DevicePlatform, PushNotificationService> pushNotificationServiceMap;

    public void send(Notification notification, UserDevice device) {

        PushNotificationService pushNotificationService = pushNotificationServiceMap.get(device.getPlatform());

        if (pushNotificationService == null)
            throw new IllegalCallerException();

        pushNotificationService.send(notification, device);
    }


}
