package api.petpassport.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotificationCreateDto {

    private String recipientEmail;
    private String subject;
    private String content;
    private String adminPass;
}
