package cm.fantasymachine.notification.mappers;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.NotificationPriority;
import cm.fantasymachine.notification.infrastructure.entities.NotificationEntity;

import java.time.Duration;

public class NotificationMapper extends AbstractMapper<Notification, NotificationEntity>{

    @Override
    public Notification toDomainObject(NotificationEntity entity) {

        if (entity == null)
            return null;


        return Notification.builder()
                .data(entity.getData())
                .id(entity.getId())
                .userId(entity.getUserId())
                .content(entity.getContent())
                .subject(entity.getSubject())
                .ttl(entity.getTtl() == null ? null : Duration.ofSeconds(entity.getTtl()))
                .missingDeviceIds(entity.getMissingDeviceIds())
                .successDeviceIds(entity.getSuccessDeviceIds())
                .tenantId(entity.getTenantId())
                .severity(entity.getSeverity())
                .status(entity.getStatus())
                .type(entity.getType())
                .priority(entity.getPriority() == null ? NotificationPriority.NORMAL : NotificationPriority.values()[entity.getPriority()])
                .timestamp(entity.getTimestamp())
                .build();
    }

    @Override
    public NotificationEntity toEntityObject(Notification domainObject) {

        if (domainObject == null)
            return null;

        return NotificationEntity.builder()
                .data(domainObject.getData())
                .id(domainObject.getId())
                .userId(domainObject.getUserId())
                .content(domainObject.getContent())
                .subject(domainObject.getSubject())
                .ttl(domainObject.getTtl() == null ? null : domainObject.getTtl().toSeconds())
                .missingDeviceIds(domainObject.getMissingDeviceIds())
                .successDeviceIds(domainObject.getSuccessDeviceIds())
                .tenantId(domainObject.getTenantId())
                .severity(domainObject.getSeverity())
                .status(domainObject.getStatus())
                .type(domainObject.getType())
                .priority(domainObject.getPriority() == null ? 0 : domainObject.getPriority().ordinal())
                .timestamp(domainObject.getTimestamp())
                .build();
    }

}
