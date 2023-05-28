package api.petpassport.controller;

import api.petpassport.config.security.dto.JwtDto;
import api.petpassport.controller.dto.SignInDto;
import api.petpassport.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Sign-in"
    )
    @PostMapping(value = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public JwtDto signIn(@Valid @RequestBody SignInDto signInDto) {
        return authService.signIn(signInDto);
    }

}