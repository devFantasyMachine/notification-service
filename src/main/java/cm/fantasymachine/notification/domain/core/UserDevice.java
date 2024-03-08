package cm.fantasymachine.notification.domain.core;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDevice {


    private UUID id;

    @NonNull
    private String tenantId;
    @NonNull
    private String userId;
    private String deviceId;
    private DevicePlatform platform;
    private Boolean isOnline;
    private LocalDateTime lastSeen;

    private String pushNotifToken;

    public Boolean isMobileDevice() {
        return platform != null && (platform == DevicePlatform.IOS || platform == DevicePlatform.ANDROID);
    }

}

