package api.petpassport.controller.service.dto;

import api.petpassport.controller.user.dto.UserExecutorDto;
import api.petpassport.enums.PetTypeEnum;
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
public class UserExecutorServiceDto {

    private UUID id;
    private UserExecutorDto user;
    private PetTypeEnum petType;
    private String serviceType;
    private String message;
    private boolean departure;
    private Double priceFixed;
    private Double priceMin;
    private Double priceMax;
    private Instant createdAt;
    private Instant updateAt;

}
