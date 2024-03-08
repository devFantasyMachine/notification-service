package cm.fantasymachine.notification.infrastructure.service;

import cm.fantasymachine.notification.domain.core.UserDevice;
import cm.fantasymachine.notification.domain.ports.UserDeviceStore;
import cm.fantasymachine.notification.infrastructure.entities.UserDeviceEntity;
import cm.fantasymachine.notification.infrastructure.repositories.RedisUserDeviceRepository;
import cm.fantasymachine.notification.mappers.UserDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;




@Service
public class RedisUserDeviceStore implements UserDeviceStore {


    @Autowired
    RedisUserDeviceRepository userDeviceRepository;

    final UserDeviceMapper userDeviceMapper = new UserDeviceMapper();


    @Override
    public Set<UserDevice> getDevices(String userId, String tenantId) {

        Set<UserDeviceEntity> devices = userDeviceRepository.findAllByUserIdAndTenantId(userId, tenantId);
        return new HashSet<>(userDeviceMapper.toDomainObjectList(devices));
    }

    @Override
    public Optional<UserDevice> getDevice(String userId, String tenantId, String deviceId) {

        Optional<UserDeviceEntity> deviceEntity = userDeviceRepository.findByUserIdAndTenantIdAndDeviceId(userId, tenantId, deviceId);
        return deviceEntity.map(userDeviceMapper::toDomainObject);
    }

    @Override
    public UserDevice save(UserDevice userDevice) {

        UserDeviceEntity deviceEntity = userDeviceRepository.save(userDeviceMapper.toEntityObject(userDevice));
        return userDeviceMapper.toDomainObject(deviceEntity);
    }


}
