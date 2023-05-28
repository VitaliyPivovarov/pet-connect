package api.petpassport.adapter;

import api.petpassport.adapter.dto.WsChatMessageDto;
import api.petpassport.service.enrich.UserEnrichService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ChatMessageOutput {

    private final SimpMessageSendingOperations messagingTemplate;
    private final UserEnrichService userEnrichService;

    public void sendMessage(UUID chatId, WsChatMessageDto wsChatMessageDto) {
        userEnrichService.setOnlineStatus(wsChatMessageDto.getUser());
        messagingTemplate.convertAndSend("/topic/chat/" + chatId.toString(),
                wsChatMessageDto);
    }

}
