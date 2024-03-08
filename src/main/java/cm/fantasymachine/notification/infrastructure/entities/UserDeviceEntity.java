package cm.fantasymachine.notification.infrastructure.entities;


import cm.fantasymachine.notification.domain.core.DevicePlatform;
import cm.fantasymachine.notification.domain.core.NotificationSeverity;
import cm.fantasymachine.notification.domain.core.NotificationStatus;
import cm.fantasymachine.notification.domain.core.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


@RedisHash("UserDeviceEntity")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDeviceEntity {

    @Id
    private UUID id;

    @Indexed
    private String deviceId;
    private DevicePlatform platform;
    private Boolean isOnline;
    private LocalDateTime lastSeen;

    private String pushNotifToken;

    @Indexed
    private String tenantId;
    @Indexed
    private String userId;

}
