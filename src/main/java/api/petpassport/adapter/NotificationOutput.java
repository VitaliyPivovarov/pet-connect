package api.petpassport.adapter;

import api.petpassport.adapter.dto.WsNotificationDto;
import api.petpassport.service.ws.ObserveUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationOutput {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ObserveUserService observeUserService;

    public void sendMessage(UUID userId, WsNotificationDto notificationDto) {
        observeUserService.findWsUserNameByUserId(userId)
                .ifPresent(user -> messagingTemplate.convertAndSend("/topic/notification/",
                        notificationDto));
    }

}
