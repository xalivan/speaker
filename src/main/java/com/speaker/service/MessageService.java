package com.speaker.service;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.Message;

import java.util.List;
import java.util.Set;

public interface MessageService {
    List<Message> getMessagesByAccountId(int accountId);

    List<List<ValidatorError>> addMessage(List<MessageDTO> messageDTO);
}
