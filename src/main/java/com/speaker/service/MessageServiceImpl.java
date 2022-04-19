package com.speaker.service;

import com.speaker.convertors.MessageConvertor;
import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;
import com.speaker.repository.AccountRepository;
import com.speaker.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.speaker.service.util.StringParser.splitBySpace;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final MessageConvertor messageConvertor;

    @Override
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findAllByAccountId(accountId);
    }

    @Override
    public Response addMessage(List<MessageDTO> messageDTOs) {
        List<Message> messages = messageDTOs.stream()
                .map(this::convertToMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (messageDTOs.size() == messages.size()) {
            return messageRepository.createMessages(messages) ? Response.TRUE : Response.FALSE;
        }
        return Response.FALSE;
    }

    private Message convertToMessage(MessageDTO messageDTO) {
        Optional<Integer> accountId = findAccountByNames(messageDTO.getToAccountNames());
        Optional<Integer> friendId = findAccountByNames(messageDTO.getFromAccountNames());
        if (accountId.isPresent() && friendId.isPresent()) {
            return messageConvertor.convertToMessage(
                    messageDTO,
                    accountId.get(),
                    friendId.get());
        }
        return null;
    }

    private Optional<Integer> findAccountByNames(String names) {
        return splitBySpace(names)
                .flatMap(name ->
                        accountRepository.findAccountIdByNameAndLastName(name.getFirst(), name.getSecond()));
    }
}
