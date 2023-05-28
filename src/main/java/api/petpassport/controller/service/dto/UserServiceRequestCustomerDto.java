package api.petpassport.controller.service.dto;

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
public class UserServiceRequestCustomerDto {

    private UUID id;
    private UserExecutorServiceDto userService;
    private Instant createdAt;
    private Instant updateAt;

}
