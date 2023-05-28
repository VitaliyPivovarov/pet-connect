package api.petpassport.service;

import api.petpassport.adapter.ChatMessageOutput;
import api.petpassport.adapter.dto.WsChatMessageDto;
import api.petpassport.controller.dto.ChatCreateUpdateDto;
import api.petpassport.controller.user.dto.UserShortDto;
import api.petpassport.domain.chat.ChatEntity;
import api.petpassport.domain.chat.ChatMessageEntity;
import api.petpassport.domain.chat.ChatMessageRepository;
import api.petpassport.domain.chat.ChatRepository;
import api.petpassport.domain.user.UserEntity;
import api.petpassport.domain.user.UserRepository;
import api.petpassport.exception.ConflictException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageOutput chatMessageOutput;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveSendMessage(UUID userId, UUID chatId, String value) {
        userRepository.findById(userId)
                .ifPresent(user -> {
                    ChatMessageEntity chatMessage = saveMessage(user, chatId, value);
                    chatMessageOutput.sendMessage(
                            chatId,
                            new WsChatMessageDto(chatMessage.getId(),
                                    objectMapper.convertValue(user, UserShortDto.class),
                                    value,
                                    chatMessage.getCreatedAt()));
                });
    }

    @Transactional
    public ChatMessageEntity saveMessage(UserEntity user, UUID chatId, String value) {
        chatRepository.findById(chatId).orElseThrow(() -> new ConflictException("Chat not found!"));
        ChatMessageEntity chatMessage = new ChatMessageEntity();
        chatMessage.setChatId(chatId);
        chatMessage.setUser(user);
        chatMessage.setValue(value);
        chatMessage.setCreatedAt(Instant.now());
        chatMessage.setUpdateAt(Instant.now());
        return chatMessageRepository.save(chatMessage);
    }

    @Transactional
    public ChatEntity createChat(UUID userId, ChatCreateUpdateDto createDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found!"));
        ChatEntity chat = new ChatEntity();
        chat.setUser(user);
        chat.setName(createDto.getName());
        chat.setAvatarUri(createDto.getAvatarUri());
        chat.setCreatedAt(Instant.now());
        chat.setUpdateAt(Instant.now());
        return chatRepository.save(chat);
    }

    public Page<ChatEntity> findAllChatByName(String name, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return chatRepository.findAllByNameStartingWith(name, pageable);
    }

    public Page<ChatMessageEntity> findAllChatMessagesByChatId(UUID chatId, final int page, final int size) {
        Pageable pageable = PageRequest.of(page, size);
        return chatMessageRepository.findAllByChatIdOrderByCreatedAtDesc(chatId, pageable);
    }
}
