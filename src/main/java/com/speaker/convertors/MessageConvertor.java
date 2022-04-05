package com.speaker.convertors;

import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConvertor {
    public MessageDTO convertTo(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .fromAccountId(message.getFromAccountId())
                .toAccountId(message.getToAccountId())
                .date(message.getDate())
                .status(message.getStatus())
                .text(message.getText())
                .build();
    }
}
