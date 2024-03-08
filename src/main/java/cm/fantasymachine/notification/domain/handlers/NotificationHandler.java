package cm.fantasymachine.notification.domain.handlers;

import cm.fantasymachine.notification.domain.core.*;
import cm.fantasymachine.notification.domain.ports.NotificationEventProducer;
import cm.fantasymachine.notification.domain.ports.NotificationStore;
import cm.fantasymachine.notification.domain.ports.UserDeviceStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;



@Slf4j
@AllArgsConstructor
public class NotificationHandler {


    private NotificationStore notificationStore;
    private UserDeviceStore userDeviceStore;
    private PushNotificationService pushNotificationService;
    private NotificationEventProducer notificationEventProducer;


    private final NotificationFactory notificationFactory = new NotificationFactory();


    public void onReceiveNotification(String userId,
                                      String deviceId,
                                      String tenantId,
                                      NotificationContent content) {

        log.info("receive new notification for user " + userId);

        Set<UserDevice> devices = userDeviceStore.getDevices(userId, tenantId);

        if (deviceId != null) {

            devices.stream()
                    .filter(userDevice -> deviceId.equals(userDevice.getDeviceId()))
                    .findAny()
                    .ifPresent(userDevice -> {

                        Notification notification =
                                notificationStore.save(
                                        notificationFactory.create(userDevice, content)
                                );

                        if (userDevice.getIsOnline())
                            notificationEventProducer.publish(notification);

                        if (userDevice.isMobileDevice())
                            pushNotificationService.send(notification, userDevice);

                    });
        } else {

            Notification notification =
                    notificationStore.save(
                            notificationFactory.create(devices, content)
                    );

            devices.stream()
                    .filter(UserDevice::isMobileDevice)
                    .forEach(device ->  pushNotificationService.send(notification, device));

            if (devices.stream().anyMatch(UserDevice::getIsOnline))
                notificationEventProducer.publish(notification);

        }
    }



    public void sendUserDeviceNotifications(UserSession userSession) {

        log.info("retrieve user {} notification", userSession.getUserId());

        for (NotificationPriority priority : NotificationPriority.values()) {

            log.info("retrieve user {} notification with priority {}", userSession.getUserId(), priority);

            Set<Notification> notifications =
                    notificationStore.getUnSendByUserIdTenantId(userSession.getUserId(), userSession.getTenantId(), priority)
                            .stream()
                            .filter(notification -> notification.getMissingDeviceIds().contains(userSession.getDeviceId()))
                            .sorted(Comparator.comparing(Notification::getTimestamp))
                            .peek(notification -> {

                                try {

                                    userSession.sendNotification(notification);
                                    notification.addSuccessDeviceId(userSession.getDeviceId());

                                } catch (Exception ignored) {}
                            })
                            .collect(Collectors.toSet());

            if (!notifications.isEmpty())
                notificationStore.saveAll(notifications);
        }

    }





}
