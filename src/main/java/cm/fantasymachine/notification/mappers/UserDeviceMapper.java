package cm.fantasymachine.notification.mappers;

import cm.fantasymachine.notification.domain.core.UserDevice;
import cm.fantasymachine.notification.infrastructure.entities.UserDeviceEntity;

public class UserDeviceMapper extends AbstractMapper<UserDevice, UserDeviceEntity>{

    @Override
    public UserDevice toDomainObject(UserDeviceEntity entity) {
        return entity == null ? null : UserDevice.builder()
                .id(entity.getId())
                .deviceId(entity.getDeviceId())
                .userId(entity.getUserId())
                .isOnline(entity.getIsOnline())
                .lastSeen(entity.getLastSeen())
                .platform(entity.getPlatform())
                .tenantId(entity.getTenantId())
                .build();
    }

    @Override
    public UserDeviceEntity toEntityObject(UserDevice domainObject) {
        return domainObject == null ? null : UserDeviceEntity.builder()
                .id(domainObject.getId())
                .deviceId(domainObject.getDeviceId())
                .userId(domainObject.getUserId())
                .isOnline(domainObject.getIsOnline())
                .lastSeen(domainObject.getLastSeen())
                .platform(domainObject.getPlatform())
                .tenantId(domainObject.getTenantId())
                .build();
    }
}
