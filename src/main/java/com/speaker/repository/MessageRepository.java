package com.speaker.repository;

import com.speaker.entities.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAllById(int id);
    int insert(Message message);
}
