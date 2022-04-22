package com.speaker.service;

import com.speaker.convertors.MessageConvertor;
import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.Message;
import com.speaker.entities.Status;
import com.speaker.repository.AccountRepository;
import com.speaker.repository.MessageRepository;
import com.speaker.service.validator.FieldValidators;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    private static final int ACCOUNT_ID = 1;
    private static final int FRIEND_ID = 2;
    private static final int INDEX_0 = 0;
    private static final int INDEX_1 = 1;
    private static final boolean INSERT_TRUE = true;
    private static final String ACCOUNT_FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Mazur";
    private static final String SEPARATOR = " ";
    private static final String ACCOUNT_FIRST_LAST_NAME = ACCOUNT_FIRST_NAME + SEPARATOR + LAST_NAME;
    private static final String FRIEND_FIRST_NAME = "Victor";
    private static final String FRIEND_FIRST_LAST_NAME = FRIEND_FIRST_NAME + SEPARATOR + LAST_NAME;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageConvertor messageConvertor;
    @Mock
    private FieldValidators<MessageDTO> messageDTOValidators;
    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    public void getMessagesByAccountIdSuccess() {
        List<Message> messages = generateMessageList();
        when(messageRepository.findAllByAccountId(ACCOUNT_ID)).thenReturn(messages);
        assertThat(messageService.getMessagesByAccountId(ACCOUNT_ID), is(messages));
    }

    @Test
    public void createdMessageSuccess() {
        List<Message> message = generateMessageList();
        List<MessageDTO> messageDTOs = generateMessageDTOList(ACCOUNT_FIRST_LAST_NAME, FRIEND_FIRST_LAST_NAME);
        List<ValidatorError> validatorErrors = generateValidatorErrors(messageDTOs, "messages was created", MessageDTO.class.getSimpleName());
        when(messageDTOValidators.validate(messageDTOs)).thenReturn(List.of());
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(ACCOUNT_ID));
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID));
        when(messageConvertor.convertToMessage(messageDTOs.get(INDEX_0), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_0));
        when(messageConvertor.convertToMessage(messageDTOs.get(1), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_1));
        when(messageRepository.createMessages(message)).thenReturn(INSERT_TRUE);
        assertThat(messageService.addMessage(messageDTOs), is(validatorErrors));
    }

    @Test
    public void messageNotCreatedWhenValidatorHasErrors() {
        List<MessageDTO> messageDTOs = generateMessageDTOList(ACCOUNT_FIRST_LAST_NAME, FRIEND_FIRST_LAST_NAME);
        List<ValidatorError> validatorErrors = generateValidatorErrors(messageDTOs, "messages no created", MessageDTO.class.getSimpleName());
        when(messageDTOValidators.validate(messageDTOs)).thenReturn(validatorErrors);
        assertThat(messageService.addMessage(messageDTOs), is(validatorErrors));
    }

    @Test
    public void messageNotCreatedWhenAccountNameIsNull() {
        List<MessageDTO> messageDTOs = generateMessageDTOList(ACCOUNT_FIRST_LAST_NAME, FRIEND_FIRST_LAST_NAME);
        List<ValidatorError> validatorErrors = generateValidatorErrors(messageDTOs, "messages no created", MessageDTO.class.getSimpleName());
        List<MessageDTO> messageDTO = generateMessageDTOList(null, FRIEND_FIRST_LAST_NAME);
        assertThat(messageService.addMessage(messageDTO), is(validatorErrors));
        verify(accountRepository, times(2)).findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME);
    }

    private List<Message> generateMessageList() {
        return List.of(Message.builder()
                        .id(5)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text("text 1")
                        .date(LocalDateTime.now())
                        .status(Status.NEW)
                        .build(),
                Message.builder()
                        .id(6)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text("text 2")
                        .date(LocalDateTime.now())
                        .status(Status.READ)
                        .build());
    }

    private List<MessageDTO> generateMessageDTOList(String accountName, String friendName) {
        return List.of(MessageDTO.builder()
                        .id(5)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text("text 1")
                        .date(LocalDateTime.now())
                        .status(Status.NEW)
                        .fromAccountNames(accountName)
                        .toAccountNames(friendName)
                        .build(),
                MessageDTO.builder()
                        .id(6)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text("text 2")
                        .date(LocalDateTime.now())
                        .status(Status.READ)
                        .fromAccountNames(accountName)
                        .toAccountNames(friendName)
                        .build());
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
}