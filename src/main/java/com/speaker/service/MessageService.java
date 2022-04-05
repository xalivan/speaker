package com.speaker.service;

import com.speaker.entities.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByAccountId(int accountId);

    int create(Message message);
}
