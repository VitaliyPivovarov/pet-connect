package api.petpassport.controller;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.PageDto;
import api.petpassport.controller.dto.PetCreateUpdateDto;
import api.petpassport.controller.dto.PetDto;
import api.petpassport.domain.PetEntity;
import api.petpassport.enums.PetTypeEnum;
import api.petpassport.service.PetService;
import api.petpassport.service.enrich.UserEnrichService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping(value = "/pets")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PetsController {

    private final PetService petService;
    private final ObjectMapper objectMapper;
    private final UserEnrichService userEnrichService;

    @Operation(
            summary = "Find all pet types", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public PetTypeEnum[] findAllPetTypes() {
        return PetTypeEnum.values();
    }

    @Operation(
            summary = "Create pet", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDto create(@RequestBody @Valid PetCreateUpdateDto createDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(petService.create(principalDto.getUserId(),
                createDto), PetDto.class);
    }

    @Operation(
            summary = "Find my pets", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<PetDto> findByUserId(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<PetEntity> result = petService.findByUserId(principalDto.getUserId(), page, size);
        return new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<PetDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
    }

    @Operation(
            summary = "Find by id"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDto findById(@PathVariable(value = "petId") UUID petId) {
        PetDto petDto = objectMapper.convertValue(petService.getById(petId), PetDto.class);
        petDto.getUsers().forEach(userEnrichService::setOnlineStatus);
        return petDto;
    }

    @Operation(
            summary = "Update pet", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PetDto update(@PathVariable(value = "petId") UUID petId,
                         @RequestBody @Valid PetCreateUpdateDto updateDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(petService.update(principalDto.getUserId(), petId, updateDto), PetDto.class);
    }

    @Operation(
            summary = "Delete pet", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "petId") UUID petId) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        petService.delete(principalDto.getUserId(), petId);
    }

}
