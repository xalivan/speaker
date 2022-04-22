package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.entities.Status;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class MessageTextValidatorImplTest {
    private static final int ACCOUNT_ID = 1;
    private static final int FRIEND_ID = 2;
    private static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now();
    @InjectMocks
    private MessageTextValidatorImpl messageTextValidator;

    @Test
    public void validateSuccess() {
        MessageDTO messageDTO = generateMessageDTO(ACCOUNT_ID, FRIEND_ID, "text", LOCAL_DATE_TIME_NOW, Status.NEW);
        EntityField entityField = generateEntityField(messageDTO);
        assertNull(messageTextValidator.validate(entityField));
    }

    @Test
    public void validateWhenTextIsEmpty() {
        MessageDTO messageDTO = generateMessageDTO(ACCOUNT_ID, FRIEND_ID, null, LOCAL_DATE_TIME_NOW, Status.NEW);
        EntityField entityField = generateEntityField(messageDTO);
        ValidatorError validatorError = generateValidatorError(ErrorType.EMPTY);
        assertThat(messageTextValidator.validate(entityField), is(validatorError));
    }

    @Test
    public void validateWhenTextNotValid() {
        MessageDTO messageDTO = generateMessageDTO(ACCOUNT_ID, FRIEND_ID, "", LOCAL_DATE_TIME_NOW, Status.NEW);
        EntityField entityField = generateEntityField(messageDTO);
        ValidatorError validatorError = generateValidatorError(ErrorType.NOT_VALID);
        assertThat(messageTextValidator.validate(entityField), is(validatorError));
    }

    @Test
    public void getField() {
        MessageDTO messageDTO = generateMessageDTO(ACCOUNT_ID, FRIEND_ID, "text", LOCAL_DATE_TIME_NOW, Status.NEW);
        assertThat(messageTextValidator.getField(messageDTO).getField(), is(new EntityField(messageDTO.getText()).getField()));
    }

    @Test
    public void validateList() {
        List<MessageDTO> messageDTOs = generateMessageDTOList();
        List<EntityField> entityFields = generateEntityFields(messageDTOs);
        assertThat(messageTextValidator.validateList(entityFields), is(generateValidatorErrors(entityFields)));
    }

    @Test
    public void getFields() {
        List<MessageDTO> messageDTOs = generateMessageDTOList();
        List<EntityField> entityFields = generateEntityFields(messageDTOs);
        assertThat(messageTextValidator.getFields(messageDTOs).get(0).getField(), is(entityFields.get(0).getField()));
        assertThat(messageTextValidator.getFields(messageDTOs).get(1).getField(), is(entityFields.get(1).getField()));
    }

    @Test
    public void getType() {
        assertThat(messageTextValidator.getType(), is(FieldType.TEXT));
    }

    private List<ValidatorError> generateValidatorErrors(List<EntityField> entityFields) {
        return entityFields.stream()
                .map(el -> generateValidatorErrorEmpty())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private MessageDTO generateMessageDTO(int fromAccountId, int toAccountId, String text, LocalDateTime date, Status status) {
        return MessageDTO.builder()
                .id(5)
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .text(text)
                .date(date)
                .status(status)
                .build();
    }

    private List<MessageDTO> generateMessageDTOList() {
        MessageDTO messageDTO = generateMessageDTO(ACCOUNT_ID, FRIEND_ID, "text", LOCAL_DATE_TIME_NOW, Status.NEW);
        return List.of(messageDTO, messageDTO);
    }

    private EntityField generateEntityField(MessageDTO messageDTO) {
        return EntityField.builder()
                .field(messageDTO.getText())
                .build();
    }

    private List<EntityField> generateEntityFields(List<MessageDTO> messageDTOs) {
        return messageDTOs.stream()
                .map(this::generateEntityField)
                .collect(Collectors.toList());
    }

    private ValidatorError generateValidatorError(ErrorType errorType) {
        return ValidatorError.builder()
                .message(errorType.getError())
                .field(generateType().getFieldName())
                .build();
    }

    private ValidatorError generateValidatorErrorEmpty() {
        return null;
    }

    private FieldType generateType() {
        return FieldType.TEXT;
    }
}
