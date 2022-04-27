package com.speaker.service;

public interface KafkaHandlerErrorsService<T> {
    void listen(String message);

    void sendConsumer(T entity);
}
