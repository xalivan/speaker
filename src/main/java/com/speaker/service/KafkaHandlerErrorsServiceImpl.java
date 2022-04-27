package com.speaker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaHandlerErrorsServiceImpl<T> implements KafkaHandlerErrorsService<T> {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @KafkaListener(topics = "messages", groupId = "group-id")
    public void listen(String message) {
        System.out.println("Received Message in group - group-id: " + message);
    }

    @Override
    public void sendConsumer(T entity) {
        kafkaTemplate.send("messages", String.valueOf(entity));
    }
}
