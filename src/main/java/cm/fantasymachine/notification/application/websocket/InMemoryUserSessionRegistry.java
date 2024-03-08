package cm.fantasymachine.notification.application.websocket;

import cm.fantasymachine.notification.domain.core.UserSession;
import cm.fantasymachine.notification.domain.ports.UserSessionRegistry;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Component
public class InMemoryUserSessionRegistry implements UserSessionRegistry {

    private final ConcurrentMap<String, UserSession> userSessionByUserIdTenantIdDeviceId =
            new ConcurrentHashMap<>();
    private final ConcurrentMap<String, UserSession> userSessionBySessionId=
            new ConcurrentHashMap<>();


    @Override
    public Optional<UserSession> getUserSession(String userId, String tenantId, String deviceId) {

        String key = userId + "." + deviceId + "." + tenantId;

        return Optional.ofNullable(userSessionByUserIdTenantIdDeviceId.get(key));
    }

    @Override
    public void register(UserSession session) {

        String key = session.getUserId() + "." + session.getDeviceId() + "." + session.getTenantId();

        System.out.println("register " + key);

        userSessionByUserIdTenantIdDeviceId.put(key, session);
        userSessionBySessionId.put(session.getSessionId(), session);

    }

    @Override
    public Optional<UserSession> getUserSessionBySessionId(String id) {
        return Optional.ofNullable(userSessionBySessionId.get(id));
    }

    @Override
    public void remove(UserSession userSession) {
        userSessionBySessionId.remove(userSession.getSessionId());
        String key = userSession.getUserId() + "." + userSession.getDeviceId() + "." + userSession.getTenantId();
        userSessionByUserIdTenantIdDeviceId.remove(key);
    }


}
