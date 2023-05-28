package api.petpassport.controller;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.ClinicalExaminationCreateUpdateDto;
import api.petpassport.controller.dto.ClinicalExaminationDto;
import api.petpassport.controller.dto.PageDto;
import api.petpassport.domain.ClinicalExaminationEntity;
import api.petpassport.service.ClinicalExaminationService;
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

@RequestMapping(value = "pet/{petId}/clinical-examinations")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ClinicalExaminationController {

    private final ClinicalExaminationService clinicalExaminationService;
    private final ObjectMapper objectMapper;

    @Operation(
            summary = "Create clinical-examination", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ClinicalExaminationDto create(@PathVariable(value = "petId") UUID petId,
                                         @RequestBody @Valid ClinicalExaminationCreateUpdateDto createDto) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        return objectMapper.convertValue(clinicalExaminationService.create(principalDto.getUserId(),
                petId, createDto), ClinicalExaminationDto.class);
    }

    @Operation(
            summary = "Delete clinical-examination", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{vaccinationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "petId") UUID petId,
                       @PathVariable(value = "vaccinationId") UUID vaccinationId) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        clinicalExaminationService.delete(principalDto.getUserId(), petId, vaccinationId);
    }

    @Operation(
            summary = "Find all clinical-examination by petId", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<ClinicalExaminationDto> findAllByPetId(@PathVariable(value = "petId") UUID petId,
                                                          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        Page<ClinicalExaminationEntity> result = clinicalExaminationService.findAllByPetId(principalDto.getUserId(), petId, page, size);
        return new PageDto<>(objectMapper.convertValue(result.getContent(), new TypeReference<List<ClinicalExaminationDto>>() {
        }),
                result.getPageable().getPageSize(),
                result.getPageable().getPageNumber());
    }

}
