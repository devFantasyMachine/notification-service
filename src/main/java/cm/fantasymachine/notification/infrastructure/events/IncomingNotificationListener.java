package cm.fantasymachine.notification.infrastructure.events;


import cm.fantasymachine.notification.domain.core.NotificationContent;
import cm.fantasymachine.notification.domain.core.NotificationPriority;
import cm.fantasymachine.notification.domain.handlers.NotificationHandler;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class IncomingNotificationListener {


    private static final String NORMAL_NOTIFICATION_TOPIC = "normal-incoming-notifications-topic";
    private static final String HIGHER_NOTIFICATION_TOPIC = "higher-incoming-notifications-topic";


    @Autowired
    NotificationHandler notificationHandler;


    /**
     *  Event listener for incoming notification with normal priority
     */
    @PulsarListener(
            subscriptionName = "incoming-notifications-topic-subscription",
            topics = NORMAL_NOTIFICATION_TOPIC,
            schemaType = SchemaType.JSON,
            subscriptionType = SubscriptionType.Shared
    )
    public void incomingNormalNotificationTopicListener(IncomingNotification incomingNotification) {

        notificationHandler.onReceiveNotification(
                incomingNotification.getUserId(),
                incomingNotification.getDeviceId(),
                incomingNotification.getTenantId(),
                NotificationContent.builder()
                        .priority(NotificationPriority.NORMAL)
                        .content(incomingNotification.getContent())
                        .content(incomingNotification.getContent())
                        .subject(incomingNotification.getSubject())
                        .data(incomingNotification.getData())
                        .ttl(incomingNotification.getTtl() == null ? null : Duration.ofSeconds(incomingNotification.getTtl()))
                        .createAt(LocalDateTime.ofEpochSecond(incomingNotification.getCreateAt(), 0, ZoneOffset.UTC))
                        .type(incomingNotification.getType())
                        .severity(incomingNotification.getSeverity())
                        .build()
        );
    }



    /**
     *  Event listener for incoming notification with higher priority
     */
    @PulsarListener(
            subscriptionName = "incoming-notifications-topic-subscription",
            topics = HIGHER_NOTIFICATION_TOPIC,
            schemaType = SchemaType.JSON,
            subscriptionType = SubscriptionType.Shared
    )
    public void incomingHigherNotificationTopicListener(IncomingNotification incomingNotification) {

        notificationHandler.onReceiveNotification(
                incomingNotification.getUserId(),
                incomingNotification.getDeviceId(),
                incomingNotification.getTenantId(),
                NotificationContent.builder()
                        .priority(NotificationPriority.HIGHER)
                        .content(incomingNotification.getContent())
                        .content(incomingNotification.getContent())
                        .subject(incomingNotification.getSubject())
                        .data(incomingNotification.getData())
                        .ttl(incomingNotification.getTtl() == null ? null : Duration.ofSeconds(incomingNotification.getTtl()))
                        .createAt(LocalDateTime.ofEpochSecond(incomingNotification.getCreateAt(), 0, ZoneOffset.UTC))
                        .type(incomingNotification.getType())
                        .severity(incomingNotification.getSeverity())
                        .build()
        );
    }





}
