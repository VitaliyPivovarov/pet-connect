package api.petpassport.controller.service.dto;

import api.petpassport.controller.user.dto.UserCustomerDto;
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
public class UserServiceRequestExecutorDto {

    private UUID id;
    private UserCustomerDto user;
    private UserExecutorServiceDto userService;
    private Instant createdAt;
    private Instant updateAt;

}
