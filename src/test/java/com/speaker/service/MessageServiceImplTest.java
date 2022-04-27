package com.speaker.service;

import com.speaker.convertors.MessageConvertor;
import com.speaker.dto.BaseEntityDTO;
import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.Message;
import com.speaker.entities.Status;
import com.speaker.repository.AccountRepository;
import com.speaker.repository.MessageRepository;
import com.speaker.service.validator.FieldValidators;
import com.speaker.service.validator.type.ErrorType;
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
    private static final String FIELD_TEXT = "text";
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
    @Mock
    private KafkaHandlerErrorsServiceImpl<BaseEntityDTO> kafkaHandlerErrorsService;
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
        List<MessageDTO> messageDTOs = generateMessageDTOList(FIELD_TEXT);
        List<ValidatorError> validatorErrors = List.of();
        when(messageDTOValidators.validate(messageDTOs)).thenReturn(validatorErrors);
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(ACCOUNT_ID));
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID));
        List<Message> message = generateMessageList();
        when(messageConvertor.convertToMessage(messageDTOs.get(INDEX_0), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_0));
        when(messageConvertor.convertToMessage(messageDTOs.get(INDEX_1), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_1));
        when(messageRepository.createMessages(message)).thenReturn(INSERT_TRUE);
        assertThat(messageService.addMessage(messageDTOs), is(validatorErrors));
        verify(kafkaHandlerErrorsService,  never()).sendConsumer(messageDTOs.get(INDEX_0));
    }

    @Test
    public void oneMessageNotCreatedWhenTextFieldHasSpaces() {
        List<MessageDTO> messageDTOs = generateMessageDTOList("  ");
        List<ValidatorError> validatorErrors = generateValidatorErrorsFromList(List.of(messageDTOs.get(INDEX_1)), ErrorType.NOT_VALID.getError(), FIELD_TEXT);
        when(messageDTOValidators.validate(messageDTOs)).thenReturn(validatorErrors);
        assertThat(messageService.addMessage(messageDTOs), is(validatorErrors));
        verify(kafkaHandlerErrorsService,  never()).sendConsumer(messageDTOs.get(INDEX_0));
    }

    @Test
    public void oneMessageNotCreatedWhenTextFieldHasNull() {
        List<MessageDTO> messageDTOs = generateMessageDTOList(null);
        List<ValidatorError> validatorErrors = generateValidatorErrorsFromList(List.of(messageDTOs.get(INDEX_1)), ErrorType.EMPTY.getError(), FIELD_TEXT);
        when(messageDTOValidators.validate(messageDTOs)).thenReturn(validatorErrors);
        assertThat(messageService.addMessage(messageDTOs), is(validatorErrors));
        verify(kafkaHandlerErrorsService,  never()).sendConsumer(messageDTOs.get(INDEX_0));
    }

    @Test
    public void oneMessageBeCreatedIfSecondMessageTextFieldHasErrors() {
        List<MessageDTO> messageDTOs = generateMessageDTOList("  ");
        List<ValidatorError> validatorErrors = generateValidatorErrorsFromList(List.of(messageDTOs.get(1)), ErrorType.NOT_VALID.getError(), FIELD_TEXT);
        when(messageDTOValidators.validate(messageDTOs)).thenReturn(validatorErrors);
        List<Message> message = List.of(generateMessageList().get(INDEX_0));
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(ACCOUNT_ID));
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID));
        when(messageConvertor.convertToMessage(messageDTOs.get(INDEX_0), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_0));
        when(messageRepository.createMessages(message)).thenReturn(INSERT_TRUE);
        assertThat(messageService.addMessage(messageDTOs), is(validatorErrors));
        verify(messageRepository).createMessages(message);
        verify(kafkaHandlerErrorsService,  never()).sendConsumer(messageDTOs.get(INDEX_0));
    }

    private List<Message> generateMessageList() {
        return List.of(Message.builder()
                        .id(10)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text(FIELD_TEXT)
                        .date(LocalDateTime.now())
                        .status(Status.NEW)
                        .build(),
                Message.builder()
                        .id(20)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text(FIELD_TEXT)
                        .date(LocalDateTime.now())
                        .status(Status.NEW)
                        .build());
    }

    private List<MessageDTO> generateMessageDTOList(String text) {
        return List.of(MessageDTO.builder()
                        .id(10)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text(FIELD_TEXT)
                        .date(LocalDateTime.now())
                        .status(Status.NEW)
                        .fromAccountNames(ACCOUNT_FIRST_LAST_NAME)
                        .toAccountNames(FRIEND_FIRST_LAST_NAME)
                        .build(),
                MessageDTO.builder()
                        .id(20)
                        .fromAccountId(ACCOUNT_ID)
                        .toAccountId(FRIEND_ID)
                        .text(text)
                        .date(LocalDateTime.now())
                        .status(Status.NEW)
                        .fromAccountNames(ACCOUNT_FIRST_LAST_NAME)
                        .toAccountNames(FRIEND_FIRST_LAST_NAME)
                        .build());
    }

    private List<ValidatorError> generateValidatorErrorsFromList(List<MessageDTO> messageDTOs, String message, String fields) {
        return messageDTOs.stream()
                .map(messageDTO -> ValidatorError.builder()
                        .message(message)
                        .field(fields)
                        .entityId(messageDTO.getId())
                        .build())
                .collect(Collectors.toList());
    }
}