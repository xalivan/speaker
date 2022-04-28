package com.speaker.service;

import com.speaker.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaHandlerErrorsServiceImpl implements KafkaHandlerErrorsService {

    @Override
    @KafkaListener(topics = "messages", groupId = "group-id")
    public void listen(MessageDTO message) {
        System.out.println(message.toString());
    }
}
