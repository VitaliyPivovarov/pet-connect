package api.petpassport.controller.user.dto;

import api.petpassport.annotation.CustomPasswordValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(
        exclude = "password"
)
public class UserCreateDto {

    @NotBlank(message = "Email cannot be empty!")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Not valid email!")
    private String email;

    @CustomPasswordValidation
    @NotBlank(message = "Password cannot be empty!")
    private String password;

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
