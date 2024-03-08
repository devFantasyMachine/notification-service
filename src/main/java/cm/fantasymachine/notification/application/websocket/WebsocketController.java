package cm.fantasymachine.notification.application.websocket;

import cm.fantasymachine.notification.domain.core.DevicePlatform;
import cm.fantasymachine.notification.domain.core.Notification;
import cm.fantasymachine.notification.domain.core.NotificationPriority;
import cm.fantasymachine.notification.domain.core.UserDevice;
import cm.fantasymachine.notification.domain.handlers.NotificationHandler;
import cm.fantasymachine.notification.domain.ports.NotificationStore;
import cm.fantasymachine.notification.domain.ports.UserDeviceStore;
import cm.fantasymachine.notification.domain.ports.UserSessionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;



@Slf4j
@Component
public class WebsocketController extends TextWebSocketHandler {


    @Autowired
    UserSessionRegistry userSessionRegistry;

    @Autowired
    NotificationHandler notificationHandler;

    @Autowired
    UserDeviceStore userDeviceStore;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Principal principal = session.getPrincipal();

        String deviceId;

        if (principal instanceof JwtAuthenticationToken jwtAuthenticationToken) {

            deviceId = jwtAuthenticationToken.getToken().getClaimAsString("deviceId");
            String query = Objects.requireNonNull(session.getUri()).getQuery();
            System.out.println(query);
        } else {
            deviceId = null;
        }

        assert deviceId != null;

        userDeviceStore.getDevice(principal.getName(), "letsgo", deviceId)
                .ifPresentOrElse(userDevice -> {

                    userDevice.setIsOnline(true);
                    userDevice.setLastSeen(LocalDateTime.now());
                    userDeviceStore.save(userDevice);
                    System.out.println("old");

                }, () -> {

                    UserDevice userDevice = UserDevice.builder()
                            .id(UUID.randomUUID())
                            .deviceId(deviceId)
                            .userId(principal.getName())
                            .isOnline(true)
                            .lastSeen(LocalDateTime.now())
                            .platform(DevicePlatform.DESKTOP)
                            .tenantId("letsgo")
                            .build();

                    userDeviceStore.save(userDevice);
                    System.out.println("new");
                });


        SpringUserSession userSession = SpringUserSession.builder()
                .webSocketSession(session)
                .deviceId(deviceId)
                .userId(principal.getName())
                .tenantId("letsgo")
                .sessionId(session.getId())
                .build();

        userSessionRegistry.register(userSession);
        notificationHandler.sendUserDeviceNotifications(userSession);
    }



    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info("Session {} close with status {}", session.getId(), status);

        userSessionRegistry.getUserSessionBySessionId(session.getId())
                .map(userSession -> {

                    try {
                        userSession.close();
                    } catch (IOException ignored) {}

                    userSessionRegistry.remove(userSession);
                    return userSession;
                })
                .flatMap(userSession -> userDeviceStore.getDevice(userSession.getUserId(), userSession.getTenantId(), userSession.getDeviceId()))
                .ifPresent(userDevice -> {

                    userDevice.setLastSeen(LocalDateTime.now());
                    userDevice.setIsOnline(false);
                    userDeviceStore.save(userDevice);

                    System.out.print("closed device");
                    System.out.println(userDevice);
                });

    }



}

