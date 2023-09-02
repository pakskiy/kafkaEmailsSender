package com.pakskiy.kafkaemailsender.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pakskiy.kafkaemailsender.dto.EmailDto;
import com.pakskiy.kafkaemailsender.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final EmailServiceImpl emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic.name}",
            groupId = "simple",
            concurrency = "3")
    public void listenEmails(List<String> emails) {
        emails.forEach(e -> {
            log.info("consume message {}", e);
            try {
                emailService.send(objectMapper.readValue(e, EmailDto.class));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }

        });
    }
}
