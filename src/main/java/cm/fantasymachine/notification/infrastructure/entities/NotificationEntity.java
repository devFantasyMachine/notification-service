package cm.fantasymachine.notification.infrastructure.entities;


import cm.fantasymachine.notification.domain.core.NotificationSeverity;
import cm.fantasymachine.notification.domain.core.NotificationStatus;
import cm.fantasymachine.notification.domain.core.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@RedisHash("NotificationEntity")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    private UUID id;

    @Indexed
    private String tenantId;
    @Indexed
    private String userId;

    private List<String> successDeviceIds;
    private Set<String> missingDeviceIds;
    private Long ttl;
    private LocalDateTime timestamp;
    private String subject;
    private String content;
    private Map<String, String> data;
    private NotificationType type;

    @Indexed
    private NotificationStatus status;
    private NotificationSeverity severity;

    @Indexed
    private Integer priority;

}
