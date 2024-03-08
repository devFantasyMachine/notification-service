package cm.fantasymachine.notification.domain.ports;

import cm.fantasymachine.notification.domain.core.UserDevice;

import java.util.Optional;
import java.util.Set;

public interface UserDeviceStore {

    Set<UserDevice> getDevices(String userId, String tenantId);

    Optional<UserDevice> getDevice(String userId, String tenantId, String deviceId);

    /**
     * @param userDevice
     * @return
     */
    UserDevice save(UserDevice userDevice);
}
