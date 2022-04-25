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
import java.util.Set;

import static com.speaker.service.util.StringParser.splitBySpace;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


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
        Set<Integer> entityIdsWithErrors = validatorErrors.stream()
                .map(ValidatorError::getEntityId)
                .collect(toSet());
        List<Message> messages = convertToListMessages(messageDTOs, entityIdsWithErrors);
        if (!messages.isEmpty()) {
            messageRepository.createMessages(messages);
            return validatorErrors;
        }
        return validatorErrors;
    }

    private List<Message> convertToListMessages(List<MessageDTO> messageDTOList, Set<Integer> entityIdsWithError) {
        return messageDTOList.stream()
                .filter(messageDTO -> !entityIdsWithError.contains(messageDTO.getId()))
                .map(this::convertToMessage)
                .filter(Objects::nonNull)
                .collect(toList());
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
