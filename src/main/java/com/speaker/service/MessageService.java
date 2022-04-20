package com.speaker.service;

import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesByAccountId(int accountId);

    Response addMessage(List<MessageDTO> messageDTO);
}
