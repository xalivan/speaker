package com.speaker.service;

import com.speaker.convertors.MessageConvertor;
import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.Message;
import com.speaker.repository.AccountRepository;
import com.speaker.repository.MessageRepository;
import com.speaker.service.validator.FieldValidators;
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
    private final FieldValidators<MessageDTO> messageDTOValidators;

    @Override
    public List<Message> getMessagesByAccountId(int accountId) {
        return messageRepository.findAllByAccountId(accountId);
    }

    @Override
    public List<ValidatorError> addMessage(List<MessageDTO> messageDTOs) {
        List<ValidatorError> validatorErrors = messageDTOValidators.validate(messageDTOs);
        if (validatorErrors.size() > 0) {
            return validatorErrors;
        }
        List<Message> messages = messageDTOs.stream()
                .map(this::convertToMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (messageDTOs.size() == messages.size()) {
            messageRepository.createMessages(messages);
            return generateValidatorErrors(messageDTOs, "messages was created", MessageDTO.class.getSimpleName());
        }
        return generateValidatorErrors(messageDTOs, "messages no created", MessageDTO.class.getSimpleName());
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

    private List<ValidatorError> generateValidatorErrors(List<MessageDTO> messageDTOs, String message, String fields) {
        return messageDTOs.stream()
                .map(messageDTO -> ValidatorError.builder()
                        .message(message)
                        .field(fields)
                        .entityId(messageDTO.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private Optional<Integer> findAccountByNames(String names) {
        return splitBySpace(names)
                .flatMap(name ->
                        accountRepository.findAccountIdByNameAndLastName(name.getFirst(), name.getSecond()));
    }
}
