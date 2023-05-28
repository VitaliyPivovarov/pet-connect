package api.petpassport.controller.dto;

import api.petpassport.controller.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private UUID id;
    private String avatarUri;
    private UserShortDto user;
    private String name;
    private Instant createdAt;
    private Instant updateAt;
}
