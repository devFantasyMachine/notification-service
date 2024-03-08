package cm.fantasymachine.notification.domain.core;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class NotificationFactory {


    public Notification create(UserDevice userDevice, NotificationContent content) {

        return Notification.builder()
                .id(UUID.randomUUID())
                .content(content.getContent())
                .missingDeviceIds(Set.of(userDevice.getDeviceId()))
                .userId(userDevice.getUserId())
                .tenantId(userDevice.getTenantId())
                .subject(content.getSubject())
                .data(content.getData())
                .ttl(content.getTtl())
                .timestamp(LocalDateTime.now())
                .status(NotificationStatus.UN_SEND)
                .successDeviceIds(new ArrayList<>())
                .priority(content.getPriority())
                .severity(content.getSeverity())
                .type(content.getType())
                .build();
    }



    public Notification create(Set<UserDevice> devices, NotificationContent content) {

        UserDevice userDevice = devices.stream().findFirst().get();

        return Notification.builder()
                .content(content.getContent())
                .missingDeviceIds(devices.stream()
                        .map(UserDevice::getDeviceId)
                        .collect(Collectors.toSet())
                )
                .id(UUID.randomUUID())
                .userId(userDevice.getUserId())
                .tenantId(userDevice.getTenantId())
                .subject(content.getSubject())
                .data(content.getData())
                .ttl(content.getTtl())
                .timestamp(LocalDateTime.now())
                .status(NotificationStatus.UN_SEND)
                .successDeviceIds(new ArrayList<>())
                .priority(content.getPriority())
                .severity(content.getSeverity())
                .type(content.getType())
                .build();
    }




}
