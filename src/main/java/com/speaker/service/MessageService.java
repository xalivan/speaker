package com.speaker.service;

import com.speaker.dto.AccountDTO;
import com.speaker.entities.Message;

import java.util.List;

public interface MessageService {
    List<Message> findAllById(int idAccount);

    int create(Message message);
}
