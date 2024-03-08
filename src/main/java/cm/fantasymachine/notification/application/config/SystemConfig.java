package cm.fantasymachine.notification.application.config;


import cm.fantasymachine.notification.domain.core.DevicePlatform;
import cm.fantasymachine.notification.domain.handlers.NotificationHandler;
import cm.fantasymachine.notification.domain.handlers.NotificationListener;
import cm.fantasymachine.notification.domain.handlers.PushNotificationProxy;
import cm.fantasymachine.notification.domain.ports.NotificationEventProducer;
import cm.fantasymachine.notification.domain.ports.NotificationStore;
import cm.fantasymachine.notification.domain.ports.UserDeviceStore;
import cm.fantasymachine.notification.domain.ports.UserSessionRegistry;
import cm.fantasymachine.notification.infrastructure.events.RedisNotificationListener;
import cm.fantasymachine.notification.infrastructure.firebase.FirebasePushNotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Map;


@Configuration
public class SystemConfig {


    @Bean
    PushNotificationProxy pushNotificationProxy(FirebasePushNotificationService firebasePushNotificationService){

        return new PushNotificationProxy(Map.of(DevicePlatform.ANDROID, firebasePushNotificationService));
    }



    @Bean
    MessageListenerAdapter messageListener(NotificationListener notificationListener) {

        return new MessageListenerAdapter(new RedisNotificationListener(notificationListener));
    }


    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter,
                                            ChannelTopic channelTopic) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);

        return container;
    }





    @Bean
    NotificationHandler notificationHandler(NotificationStore notificationStore,
                                            UserDeviceStore userDeviceStore,
                                            PushNotificationProxy pushNotificationProxy,
                                            NotificationEventProducer notificationEventProducer) {

        return new NotificationHandler(notificationStore,
                userDeviceStore,
                pushNotificationProxy,
                notificationEventProducer);

    }

    @Bean
    NotificationListener notificationListener(NotificationStore notificationStore,
                                              UserSessionRegistry userSessionRegistry){

        return new NotificationListener(userSessionRegistry, notificationStore);
    }


}
