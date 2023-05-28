package api.petpassport.controller.dto;

import api.petpassport.enums.PetTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PetCreateUpdateDto {

    @NotBlank(message = "name is null!")
    private String name;

    @NotBlank(message = "breed is null!")
    private String breed;

    @NotBlank(message = "sex is null!")
    private String sex;

    @NotNull(message = "type is null!")
    private PetTypeEnum type;

    private String avatarUri;

    @NotNull
    private Instant dateOfBirth;
    private String coat;
    private String coatVariety;

}
