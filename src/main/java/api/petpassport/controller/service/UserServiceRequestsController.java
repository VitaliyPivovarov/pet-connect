package api.petpassport.controller.service;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.PageDto;
import api.petpassport.controller.service.dto.UserCustomerServiceDto;
import api.petpassport.controller.service.dto.UserServiceRequestCreateDto;
import api.petpassport.domain.service.UserServiceRequestsEntity;
import api.petpassport.service.enrich.UserEnrichService;
import api.petpassport.service.user.UserServiceRequestsService;
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

@RequestMapping(value = "/services/requests")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserServiceRequestsController {

    private final UserServiceRequestsService userServiceRequestsService;
    private final ObjectMapper objectMapper;
    private final UserEnrichService userEnrichService;

    @Operation(
            summary = "Create requests", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserCustomerServiceDto create(@RequestBody @Valid UserServiceRequestCreateDto createDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(userServiceRequestsService.create(principalDto.getUserId(), createDto.getId()), UserCustomerServiceDto.class);
    }

    @Operation(
            summary = "Find my requests for customers", security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "customers", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UserCustomerServiceDto> findForCustomer(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<UserServiceRequestsEntity> result = userServiceRequestsService.findForCustomer(principalDto.getUserId(), page, size);
        PageDto<UserCustomerServiceDto> pageDto = new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<UserCustomerServiceDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
        pageDto.getContent().forEach(s -> userEnrichService.setOnlineStatus(s.getUser()));
        return pageDto;
    }

    @Operation(
            summary = "Find my requests for executor", security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping(value = "executors", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UserCustomerServiceDto> findForExecutor(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<UserServiceRequestsEntity> result = userServiceRequestsService.findForExecutor(principalDto.getUserId(), page, size);
        PageDto<UserCustomerServiceDto> pageDto = new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<UserCustomerServiceDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
        pageDto.getContent().forEach(s -> userEnrichService.setOnlineStatus(s.getUser()));
        return pageDto;
    }

}
