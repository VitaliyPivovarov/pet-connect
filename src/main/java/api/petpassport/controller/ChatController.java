package api.petpassport.controller;

import api.petpassport.adapter.dto.WsChatMessageDto;
import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.ChatCreateUpdateDto;
import api.petpassport.controller.dto.ChatDto;
import api.petpassport.controller.dto.PageDto;
import api.petpassport.domain.chat.ChatEntity;
import api.petpassport.domain.chat.ChatMessageEntity;
import api.petpassport.service.ChatService;
import api.petpassport.service.enrich.UserEnrichService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "chats")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ObjectMapper objectMapper;
    private final UserEnrichService userEnrichService;

    @Operation(
            summary = "Create chat", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ChatDto create(@RequestBody @Valid ChatCreateUpdateDto createDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(chatService.createChat(principalDto.getUserId(), createDto), ChatDto.class);
    }

    @Operation(
            summary = "Find all chat by name", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<ChatDto> findAllByName(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                          @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<ChatEntity> result = chatService.findAllChatByName(name, page, size);
        return new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<ChatDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
    }

    @Operation(
            summary = "Find all messages by chatId", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<WsChatMessageDto> findAllByChatId(@PathVariable(value = "chatId") UUID chatId,
                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<ChatMessageEntity> result = chatService.findAllChatMessagesByChatId(chatId, page, size);
        PageDto<WsChatMessageDto> pageDto = new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<WsChatMessageDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
        pageDto.getContent().forEach(c -> {
            userEnrichService.setOnlineStatus(c.getUser());
        });
        return pageDto;
    }

    @Operation(
            summary = "Create message", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{chatId}/messages", produces = MediaType.TEXT_PLAIN_VALUE)
    public void createMessages(@PathVariable(value = "chatId") UUID chatId,
                               @RequestBody String message) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        chatService.saveSendMessage(principalDto.getUserId(), chatId, message);
    }
}
