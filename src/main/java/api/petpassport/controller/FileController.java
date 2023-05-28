package api.petpassport.controller;

import api.petpassport.config.security.dto.PrincipalDto;
import api.petpassport.controller.dto.FileDto;
import api.petpassport.exception.ConflictException;
import api.petpassport.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "files")
@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final ObjectMapper objectMapper;
    private static final int MAX_SIZE = 3;//3MB

    @Operation(
            summary = "Upload file", security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FileDto handleFileUpload(@RequestParam("file") MultipartFile file) {
        PrincipalDto principalDto = objectMapper.convertValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), PrincipalDto.class);
        if (file.getSize() > MAX_SIZE * 1024 * 1024) {
            throw new ConflictException(String.format("File size should be less than %d MB", MAX_SIZE));
        }
        return new FileDto(fileService.save(principalDto.getUserId(), file).getUri());
    }
}
