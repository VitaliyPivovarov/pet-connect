package api.petpassport.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @NotBlank(message = "Name cannot be empty!")
    private String name;

    @NotBlank(message = "Surname cannot be empty!")
    private String surname;

    private String communicationType;
    private String avatarUri;
    private String address;
    private String postCode;
    private String country;
    private String city;
    
}
