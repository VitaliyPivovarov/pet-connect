package api.petpassport.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(final String recipientEmail,
                          final String subject,
                          final String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(content);
        try {
            emailSender.send(message);
            log.info("Sent email to {}", recipientEmail);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
