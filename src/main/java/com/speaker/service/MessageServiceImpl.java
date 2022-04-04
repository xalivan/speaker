package com.speaker.service;

import com.speaker.entities.Message;
import com.speaker.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    public List<Message> findAllById(int idAccount) {
        return messageRepository.findAllById(idAccount);
    }

    @Override
    public int create(Message message) {
        return messageRepository.insert(message);
    }
}
