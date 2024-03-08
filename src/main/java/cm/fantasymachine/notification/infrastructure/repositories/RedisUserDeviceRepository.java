package cm.fantasymachine.notification.infrastructure.repositories;



import cm.fantasymachine.notification.infrastructure.entities.UserDeviceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RedisUserDeviceRepository extends CrudRepository<UserDeviceEntity, UUID> {

    Set<UserDeviceEntity> findAllByUserIdAndTenantId(String userId, String tenantId);

    Optional<UserDeviceEntity> findByUserIdAndTenantIdAndDeviceId(String userId, String tenantId, String deviceId);
}
