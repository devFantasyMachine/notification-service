package cm.fantasymachine.notification.infrastructure.service;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.NotificationPriority;
import cm.fantasymachine.notification.domain.core.NotificationStatus;
import cm.fantasymachine.notification.domain.ports.NotificationStore;
import cm.fantasymachine.notification.infrastructure.entities.NotificationEntity;
import cm.fantasymachine.notification.infrastructure.repositories.RedisNotificationRepository;
import cm.fantasymachine.notification.mappers.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Service
public class RedisNotificationStore implements NotificationStore {


    final NotificationMapper notificationMapper = new NotificationMapper();

    @Autowired
    RedisNotificationRepository notificationRepository;


    @Override
    public Notification save(Notification notification) {

        NotificationEntity savedEntity = notificationRepository.save(notificationMapper.toEntityObject(notification));
        return notificationMapper.toDomainObject(savedEntity);
    }


    @Override
    public void saveAll(Collection<Notification> notifications) {

        notificationRepository.saveAll(notificationMapper.toEntityList(notifications));
    }


    @Override
    public Set<Notification> getUnSendByUserIdTenantId(String userId, String tenantId, NotificationPriority priority) {

        Set<NotificationEntity> notifications =
                notificationRepository.findAllByUserIdAndTenantIdAndPriorityAndStatus(userId,
                        tenantId,
                        priority.ordinal(),
                        NotificationStatus.UN_SEND);

        return new HashSet<>(notificationMapper.toDomainObjectList(notifications));
    }


}
