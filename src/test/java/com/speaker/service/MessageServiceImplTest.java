package com.speaker.service;

import com.speaker.convertors.MessageConvertor;
import com.speaker.dto.MessageDTO;
import com.speaker.entities.Message;
import com.speaker.entities.Status;
import com.speaker.repository.AccountRepository;
import com.speaker.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void getMessagesByAccountIdSuccess() {
        List<Message> messages = generateMessageList();
        when(messageRepository.findAllByAccountId(ACCOUNT_ID)).thenReturn(messages);
        assertThat(messageService.getMessagesByAccountId(ACCOUNT_ID), is(messages));
    }

    @Test
    void addingMessageSuccess() {
        List<Message> message = generateMessageList();
        List<MessageDTO> messageDTO = generateMessageDTOList(ACCOUNT_FIRST_LAST_NAME, FRIEND_FIRST_LAST_NAME);
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(ACCOUNT_ID));
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID));
        when(messageConvertor.convertToMessage(messageDTO.get(INDEX_0), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_0));
        when(messageConvertor.convertToMessage(messageDTO.get(1), FRIEND_ID, ACCOUNT_ID)).thenReturn(message.get(INDEX_1));
        when(messageRepository.createMessages(message)).thenReturn(INSERT_TRUE);
        assertThat(messageService.addMessage(messageDTO), is(Response.TRUE));
    }

    @Test
    void messageNotAddingWhenAccountIdIsNull() {
        List<MessageDTO> messageDTO = generateMessageDTOList(ACCOUNT_FIRST_LAST_NAME, FRIEND_FIRST_LAST_NAME);
        List<Message> message = generateMessageList();
        when(accountRepository.findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME)).thenReturn(Optional.empty());
        when(accountRepository.findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME)).thenReturn(Optional.of(FRIEND_ID));
        assertThat(messageService.addMessage(messageDTO), is(Response.FALSE));
        verify(accountRepository, times(2)).findAccountIdByNameAndLastName(ACCOUNT_FIRST_NAME, LAST_NAME);
        verify(accountRepository, times(2)).findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME);
        verify(messageConvertor, never()).convertToMessage(messageDTO.get(INDEX_0), null, FRIEND_ID);
        verify(messageConvertor, never()).convertToMessage(messageDTO.get(INDEX_1), null, FRIEND_ID);
        verify(messageRepository, never()).createMessages(message);
    }

    @Test
    void messageNotAddingWhenAccountNameIsNull() {
        List<MessageDTO> messageDTO = generateMessageDTOList(null, FRIEND_FIRST_LAST_NAME);
        List<Message> message = generateMessageList();
        assertThat(messageService.addMessage(messageDTO), is(Response.FALSE));
        verify(accountRepository, never()).findAccountIdByNameAndLastName(null, null);
        verify(accountRepository, times(2)).findAccountIdByNameAndLastName(FRIEND_FIRST_NAME, LAST_NAME);
        verify(messageConvertor, never()).convertToMessage(messageDTO.get(INDEX_0), null, FRIEND_ID);
        verify(messageConvertor, never()).convertToMessage(messageDTO.get(INDEX_1), null, FRIEND_ID);
        verify(messageRepository, never()).createMessages(message);
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
}