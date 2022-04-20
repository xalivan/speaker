package com.speaker.convertors;

import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConvertor {
    public MessageDTO convertToMessageDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .fromAccountId(message.getFromAccountId())
                .toAccountId(message.getToAccountId())
                .date(message.getDate())
                .status(message.getStatus())
                .text(message.getText())
                .build();
    }

    public Message convertToMessage(MessageDTO message, Integer accountId, Integer friendId) {
        return Message.builder()
                .id(message.getId())
                .fromAccountId(accountId)
                .toAccountId(friendId)
                .date(message.getDate())
                .status(message.getStatus())
                .text(message.getText())
                .build();
    }
}
