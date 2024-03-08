package cm.fantasymachine.notification.application.websocket;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.UserSession;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;


@Builder
@ToString
@AllArgsConstructor
public class SpringUserSession implements UserSession {

    private final @NonNull WebSocketSession webSocketSession;
    private final @NonNull String sessionId;
    private final @NonNull String userId;
    private final @NonNull String deviceId;
    private final @NonNull String tenantId;


    @Override
    public @NonNull String getUserId() {
        return userId;
    }

    @Override
    public @NonNull String getDeviceId() {
        return deviceId;
    }

    @Override
    public @NonNull String getTenantId() {
        return tenantId;
    }

    @Override
    public @NonNull String getSessionId() {
        return sessionId;
    }


    @Override
    public void close() throws IOException {

        synchronized (webSocketSession){

            webSocketSession.close();
        }
    }


    @Override
    public void sendNotification(Notification notification) throws Exception {

        JsonObject jsonObject = translateToJSON(notification);

        synchronized (webSocketSession){

            webSocketSession.sendMessage(new TextMessage(jsonObject.getAsString()));
        }

    }

    private static JsonObject translateToJSON(Notification notification) {
        JsonObject jsonObject = new JsonObject();
        JsonObject data = new JsonObject();

        if (notification.getData() != null)
            notification.getData().forEach(data::addProperty);

        jsonObject.addProperty("timestamp", notification.getTimestamp().toString());
        jsonObject.addProperty("subject", notification.getSubject());
        jsonObject.addProperty("content", notification.getContent());
        jsonObject.addProperty("type", notification.getType().ordinal());
        jsonObject.addProperty("severity", notification.getSeverity().ordinal());

        jsonObject.add("data", data);

        return jsonObject;
    }

}
