package com.pakskiy.kafkaemailsender.service;

import com.pakskiy.kafkaemailsender.dto.EmailDto;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void send(EmailDto emailDto);
}
