package com.speaker.service;

import com.speaker.dto.BaseEntityDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaHandlerErrorsServiceImpl implements KafkaHandlerErrorsService<BaseEntityDTO> {
    public static final String TOPICS = "messages";
    private static final String GROUP_ID = "group-id";
    @Override
    @KafkaListener(topics = TOPICS, groupId = GROUP_ID)
    public void listen(BaseEntityDTO message) {
        System.out.println(message.toString());
    }
}
