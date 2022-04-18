package com.speaker.service;

import com.speaker.convertors.MessageConvertor;
import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;
import com.speaker.repository.AccountRepository;
import com.speaker.repository.MessageRepository;
import com.speaker.service.util.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.speaker.service.util.StringParser.parseOptionalStringBySpace;

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
    public Response addMessage(List<MessageDTO> messageDTO) {
        for (MessageDTO message : messageDTO) {
            if (parseOptionalStringBySpace(message.getToAccountNames()).isPresent()
                    && parseOptionalStringBySpace(message.getFromAccountNames()).isPresent()
                    && !messageDTO.isEmpty()) {
                if (messageRepository.insert(
                        messageConvertor.convertToMessageList(
                                messageDTO,
                                findAccountByNames(parseOptionalStringBySpace(message.getToAccountNames())),
                                findAccountByNames(parseOptionalStringBySpace(message.getFromAccountNames())))).isPresent()) {
                    return Response.TRUE;
                }
            }
        }
        return Response.FALSE;
    }

    private Optional<Integer> findAccountByNames(Optional<Pair<String, String>> names) {
        return names.map(name -> accountRepository.findAccountIdByNameAndLastName(name.getFirst(), name.getSecond()))
                .orElse(Optional.empty());
    }
}
