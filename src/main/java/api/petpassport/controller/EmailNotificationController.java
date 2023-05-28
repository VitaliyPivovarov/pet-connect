package api.petpassport.controller;

import api.petpassport.controller.dto.EmailNotificationCreateDto;
import api.petpassport.service.EmailNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "notifications")
@RestController
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationController {

    private final EmailNotificationService emailNotificationService;

    @Operation(
            summary = "Send email message", security = @SecurityRequirement(name = "bearerAuth")
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public void send(@RequestBody EmailNotificationCreateDto createDto) {
        if (!createDto.getAdminPass().equals("admin1!")) {
            log.error("notifications admin password is incorrect {}", createDto.getAdminPass());
            return;
        }
        new Thread(() -> {
            log.info("Sending email message");
            emailNotificationService.sendEmail(createDto.getRecipientEmail(),
                    createDto.getSubject(),
                    createDto.getContent());
        }).start();
    }

}
