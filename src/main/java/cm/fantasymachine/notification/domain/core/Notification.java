package cm.fantasymachine.notification.domain.core;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


/**
 * Base class for Notification Object.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private UUID id;

    /**
     * user's device information
     */
    @NonNull
    private String tenantId;
    @NonNull
    private String userId;

    private List<String> successDeviceIds;
    @NonNull
    private Set<String> missingDeviceIds;

    private Duration ttl;
    @NonNull
    private LocalDateTime timestamp;

    private String subject;
    private String content;
    private Map<String, String> data;

    @NonNull
    private NotificationType type;
    @NonNull
    private NotificationStatus status;
    @NonNull
    private NotificationSeverity severity;
    private NotificationPriority priority;


    public void addSuccessDeviceId(String deviceId) {

        if (this.successDeviceIds == null)
            this.successDeviceIds = new ArrayList<>();

        this.successDeviceIds.add(deviceId);
        this.missingDeviceIds.remove(deviceId);

        if (missingDeviceIds.isEmpty())
            status = NotificationStatus.SEND;
    }


}
