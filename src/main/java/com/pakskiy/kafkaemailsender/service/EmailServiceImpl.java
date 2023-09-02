package com.pakskiy.kafkaemailsender.service;

import com.pakskiy.kafkaemailsender.dto.EmailDto;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String from;

    @Value("${spring.mail.properties.mail.welcome-text}")
    private String welcomeText;

    @Override
    public void send(EmailDto emailDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    false,
                    "UTF-8");
            helper.setSubject("You just registered!");
            helper.setTo(emailDto.getEmail());
            helper.setFrom(from);
            String emailContent = welcomeText + " " + emailDto.getFirstName() + " " + emailDto.getLastName();
            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
