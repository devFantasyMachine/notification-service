package cm.fantasymachine.notification.infrastructure.events;

import cm.fantasymachine.notification.domain.core.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomingNotification {

    @NonNull
    private String userId;
    @NonNull
    private String tenantId;

    private String deviceId;
    private Long ttl;
    private Map<String, String> data;

    @NonNull
    private String content;
    @NonNull
    private String subject;
    @NonNull
    private Long createAt;
    @NonNull
    private NotificationType type;
    @NonNull
    private NotificationSeverity severity;

}
