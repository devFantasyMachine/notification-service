package cm.fantasymachine.notification.domain.core;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {

    private UUID groupId;
    private String groupName;
    private String description;
    private LocalDateTime createAt;
    private List<UserDevice> userDevices;
}
