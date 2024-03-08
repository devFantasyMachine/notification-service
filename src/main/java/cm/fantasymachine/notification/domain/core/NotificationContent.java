package cm.fantasymachine.notification.domain.core;


import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationContent {

    private Duration ttl;
    private String content;
    private String subject;
    private Map<String, String> data;
    private LocalDateTime createAt;

    private NotificationType type;
    private NotificationSeverity severity;
    @Getter
    private NotificationPriority priority;

}
