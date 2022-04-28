package com.speaker.service;

import com.speaker.dto.MessageDTO;

public interface KafkaHandlerErrorsService{
    void listen(MessageDTO entity);
}
