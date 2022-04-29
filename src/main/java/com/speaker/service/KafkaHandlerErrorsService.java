package com.speaker.service;

import com.speaker.dto.BaseEntityDTO;

public interface KafkaHandlerErrorsService{
    void listen(BaseEntityDTO entity);
}
