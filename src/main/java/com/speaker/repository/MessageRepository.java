package com.speaker.repository;

import com.speaker.entities.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> findAllByAccountId(int id);

    boolean createMessages(List<Message> message);
}
