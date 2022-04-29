package com.speaker.service;

public interface KafkaHandlerErrorsService<T>{
    void listen(T entity);
}
