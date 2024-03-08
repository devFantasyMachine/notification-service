package cm.fantasymachine.notification.domain.core;


import java.io.IOException;

public interface UserSession {

    String getUserId();

    String getDeviceId();

    String getTenantId();

    String getSessionId();

    void sendNotification(Notification notification) throws Exception;

    void close() throws IOException;
}
