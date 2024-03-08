package cm.fantasymachine.notification.domain.ports;


import cm.fantasymachine.notification.domain.core.UserSession;

import java.util.Optional;


public interface UserSessionRegistry {

    Optional<UserSession> getUserSession(String userId, String tenantId, String deviceId);

    void register(UserSession session);

    Optional<UserSession> getUserSessionBySessionId(String id);

    void remove(UserSession userSession);
}
