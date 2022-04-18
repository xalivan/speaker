package com.speaker.convertors;

import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Message convertToMessage(MessageDTO message) {
        return Message.builder()
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

    public List<Message> convertToMessageList(List<MessageDTO> message, Optional<Integer> accountId, Optional<Integer> friendId) {
        if (accountId.isPresent() && friendId.isPresent()) {
            return message.stream()
                    .map(ms -> Message.builder()
                            .id(ms.getId())
                            .fromAccountId(accountId.get())
                            .toAccountId(friendId.get())
                            .date(ms.getDate())
                            .status(ms.getStatus())
                            .text(ms.getText())
                            .build()).collect(Collectors.toList());

        }
        return null;
    }

}
