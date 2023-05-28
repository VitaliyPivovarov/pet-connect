package api.petpassport.controller;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.IdentificationCreateUpdateDto;
import api.petpassport.controller.dto.IdentificationDto;
import api.petpassport.service.IdentificationService;
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

@RequestMapping(value = "pet/{petId}/identifications")
@RestController
@Slf4j
@RequiredArgsConstructor
public class IdentificationsController {

    private final IdentificationService identificationService;
    private final ObjectMapper objectMapper;

    @Operation(
            summary = "Create identification", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public IdentificationDto create(@PathVariable(value = "petId") UUID petId,
                                    @RequestBody @Valid IdentificationCreateUpdateDto createDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(identificationService.create(principalDto.getUserId(),
                petId, createDto), IdentificationDto.class);
    }

    @Operation(
            summary = "Update identification", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{identificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public IdentificationDto update(@PathVariable(value = "petId") UUID petId,
                                    @PathVariable(value = "identificationId") UUID identificationId,
                                    @RequestBody @Valid IdentificationCreateUpdateDto updateDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(identificationService.update(principalDto.getUserId(), petId, identificationId, updateDto), IdentificationDto.class);
    }

    @Operation(
            summary = "Delete identification", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{identificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "petId") UUID petId,
                       @PathVariable(value = "identificationId") UUID identificationId) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        identificationService.delete(principalDto.getUserId(), petId, identificationId);
    }

    @Operation(
            summary = "Get identification", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public IdentificationDto getById(@PathVariable(value = "petId") UUID petId) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(identificationService.get(principalDto.getUserId(),
                petId), IdentificationDto.class);
    }

}
