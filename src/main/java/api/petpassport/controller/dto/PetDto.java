package api.petpassport.controller.dto;

import api.petpassport.controller.user.dto.UserCustomerDto;
import api.petpassport.enums.PetTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {

    private UUID id;
    private PetTypeEnum type;
    private String avatarUri;
    private List<UserCustomerDto> users;
    private String name;
    private String breed;
    private String sex;
    private Instant dateOfBirth;
    private String coat;
    private String coatVariety;
    private Instant createdAt;
    private Instant updateAt;
}
