package cm.fantasymachine.notification.infrastructure.events;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.handlers.NotificationListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;


//@Service

@AllArgsConstructor
public class RedisNotificationListener implements MessageListener  {

    final ObjectMapper objectMapper = new ObjectMapper();

    //@Autowired
    NotificationListener notificationListener;


    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {

            Notification notification = objectMapper.readValue(message.toString(), Notification.class);
            notificationListener.onNotification(notification);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}
