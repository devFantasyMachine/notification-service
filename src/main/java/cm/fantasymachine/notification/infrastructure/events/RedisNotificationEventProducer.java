package cm.fantasymachine.notification.infrastructure.events;

import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.ports.NotificationEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;


@Component
public class RedisNotificationEventProducer implements NotificationEventProducer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publish(Notification notification) {

        redisTemplate.convertAndSend("notifications", notification);
    }



}
