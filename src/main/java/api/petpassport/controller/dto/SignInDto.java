package api.petpassport.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    @NotBlank(message = "email can't be empty!")
    private String email;

    @NotBlank(message = "password can't be empty!")
    private String password;

}
