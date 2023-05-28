package api.petpassport.adapter.dto;

import api.petpassport.controller.user.dto.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WsChatMessageDto {

    private UUID id;
    private UserShortDto user;
    private String value;
    private Instant createdAt;
}
