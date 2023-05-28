package api.petpassport.adapter;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatMessageInput {

    private final ChatService chatService;

    @MessageMapping("/chat/{chatId}")
    public void sendMessage(@DestinationVariable String chatId,
                            @AuthenticationPrincipal PrincipalDto principalDto,
                            @Payload Message<String> message) {
        if (principalDto == null || principalDto.getUserId() == null) {
            log.error("principalDto is null");
            return;
        }
        chatService.saveSendMessage(principalDto.getUserId(), UUID.fromString(chatId), message.getPayload());
    }

}
