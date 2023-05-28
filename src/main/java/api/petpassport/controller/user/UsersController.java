package api.petpassport.controller.user;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.user.dto.UserCreateDto;
import api.petpassport.controller.user.dto.UserDto;
import api.petpassport.controller.user.dto.UserUpdateDto;
import api.petpassport.service.user.UserService;
import api.petpassport.service.enrich.UserEnrichService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(value = "/users")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final UserEnrichService userEnrichService;

    @Operation(
            summary = "Create user"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody @Valid UserCreateDto createDto) {
        userService.create(createDto);
    }

    @Operation(
            summary = "Get my info", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getMe() {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        UserDto userDto = objectMapper.convertValue(userService.getById(principalDto.getUserId()), UserDto.class);
        userEnrichService.setOnlineStatus(userDto);
        return userDto;
    }

    @Operation(
            summary = "Update user", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto update(@RequestBody @Valid UserUpdateDto updateDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(userService.update(principalDto.getUserId(), updateDto), UserDto.class);
    }

    @Operation(
            summary = "Check exist email or username"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/exist", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean exist(@RequestParam(value = "email") String email) {
        return userService.exist(email);
    }

    @Operation(
            summary = "Get user by id", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getById(@PathVariable(value = "userId") UUID userId) {
        UserDto userDto = objectMapper.convertValue(userService.getById(userId), UserDto.class);
        userEnrichService.setOnlineStatus(userDto);
        return userDto;
    }

}
