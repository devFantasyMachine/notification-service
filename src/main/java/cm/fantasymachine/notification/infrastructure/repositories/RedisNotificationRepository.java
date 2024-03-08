package cm.fantasymachine.notification.infrastructure.repositories;

import cm.fantasymachine.notification.domain.core.NotificationStatus;
import cm.fantasymachine.notification.infrastructure.entities.NotificationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;
import java.util.UUID;

public interface RedisNotificationRepository extends CrudRepository<NotificationEntity, UUID> {

    Set<NotificationEntity> findAllByUserIdAndTenantIdAndPriorityAndStatus(String userId, String tenantId, int priority, NotificationStatus unSend);

}
