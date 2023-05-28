package api.petpassport.controller.service;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.PageDto;
import api.petpassport.controller.service.dto.UserExecutorServiceDto;
import api.petpassport.controller.service.dto.UserServiceCreateUpdateDto;
import api.petpassport.domain.service.UserServicesEntity;
import api.petpassport.enums.PetTypeEnum;
import api.petpassport.service.enrich.UserEnrichService;
import api.petpassport.service.user.UserServiceService;
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

@RequestMapping(value = "/services")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserServicesController {

    private final UserServiceService userServiceService;
    private final ObjectMapper objectMapper;
    private final UserEnrichService userEnrichService;

    @Operation(
            summary = "Create service", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserExecutorServiceDto create(@RequestBody @Valid UserServiceCreateUpdateDto createDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(userServiceService.create(principalDto.getUserId(), createDto), UserExecutorServiceDto.class);
    }

    @Operation(
            summary = "Update service", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserExecutorServiceDto update(@PathVariable(value = "id") UUID id,
                                         @RequestBody @Valid UserServiceCreateUpdateDto updateDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(userServiceService.update(principalDto.getUserId(), id, updateDto), UserExecutorServiceDto.class);
    }

    @Operation(
            summary = "Delete service", security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "id") UUID id) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        userServiceService.delete(principalDto.getUserId(), id);
    }

    @Operation(
            summary = "Find my services", security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UserExecutorServiceDto> findByUserId(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                        @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<UserServicesEntity> result = userServiceService.findByUserId(principalDto.getUserId(), page, size);
        return new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<UserExecutorServiceDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
    }

    @Operation(
            summary = "Find services by name", security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UserExecutorServiceDto> search(@RequestParam(value = "pet-type", required = false) PetTypeEnum petTypeEnum,
                                                  @RequestParam(value = "serviceType", required = false) String serviceType,
                                                  @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                  @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<UserServicesEntity> result = userServiceService.search(petTypeEnum, serviceType, page, size);
        PageDto<UserExecutorServiceDto> pageDto = new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<UserExecutorServiceDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
        pageDto.getContent().forEach(s -> userEnrichService.setOnlineStatus(s.getUser()));
        return pageDto;
    }

}
