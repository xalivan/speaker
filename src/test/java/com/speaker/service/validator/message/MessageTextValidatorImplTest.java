package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.entities.Status;
import com.speaker.validator.message.MessageTextValidatorImpl;
import com.speaker.validator.type.ErrorType;
import com.speaker.validator.type.FieldType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
class MessageTextValidatorImplTest {
    private static final String ACCOUNT_FIRST_NAME = "Ivan";
    private static final String LAST_NAME = "Mazur";
    private static final String SEPARATOR = " ";
    private static final String ACCOUNT_FIRST_LAST_NAME = ACCOUNT_FIRST_NAME + SEPARATOR + LAST_NAME;
    private static final String FRIEND_FIRST_NAME = "Victor";
    private static final String FRIEND_FIRST_LAST_NAME = FRIEND_FIRST_NAME + SEPARATOR + LAST_NAME;
    private static final int ACCOUNT_ID = 1;
    private static final int FRIEND_ID = 2;
    private static final int ENTITY_FIELD_ID = 5;
    private static final String ENTITY_FIELD_TEXT = "text";
    private static final LocalDateTime LOCAL_DATE_TIME_NOW = LocalDateTime.now();
    @InjectMocks
    private MessageTextValidatorImpl messageTextValidator;

    @Test
    public void validateSuccess() {
        List<MessageDTO> messageDTOs = generateMessageDTOList(20, ENTITY_FIELD_TEXT);
        List<EntityField> entityFields = generateEntityFieldList(messageDTOs);
        assertThat(messageTextValidator.validate(entityFields.get(0)), nullValue());
        assertThat(messageTextValidator.validate(entityFields.get(1)), nullValue());
    }

    @Test
    public void validateWhenTextIsEmpty() {
        List<MessageDTO> messageDTOs = generateMessageDTOList(20, null);
        List<EntityField> entityFields = generateEntityFieldList(messageDTOs);
        assertThat(messageTextValidator.validate(entityFields.get(0)), is(createValidatorError(ErrorType.EMPTY, entityFields.get(0))));
        assertThat(messageTextValidator.validate(entityFields.get(1)), nullValue());
    }

    @Test
    public void validateWhenTextNotValid() {
        List<MessageDTO> messageDTOs = generateMessageDTOList(20, "");
        List<EntityField> entityFields = generateEntityFieldList(messageDTOs);
        assertThat(messageTextValidator.validate(entityFields.get(0)), is(createValidatorError(ErrorType.NOT_VALID, entityFields.get(0))));
        assertThat(messageTextValidator.validate(entityFields.get(1)), nullValue());
    }

    @Test
    public void getField() {
        MessageDTO messageDTO = generateMessageDTO(ENTITY_FIELD_ID, ENTITY_FIELD_TEXT);
        EntityField entityField = generateEntityField(messageDTO);
        assertThat(messageTextValidator.getField(messageDTO), is(entityField));
    }

    @Test
    public void getType() {
        assertThat(messageTextValidator.getType(), is(FieldType.TEXT));
    }

    private MessageDTO generateMessageDTO(Integer id, String text) {
        return MessageDTO.builder()
                .id(id)
                .fromAccountId(ACCOUNT_ID)
                .toAccountId(FRIEND_ID)
                .text(text)
                .date(LOCAL_DATE_TIME_NOW)
                .status(Status.NEW)
                .fromAccountNames(ACCOUNT_FIRST_LAST_NAME)
                .toAccountNames(FRIEND_FIRST_LAST_NAME)
                .build();
    }

    private List<MessageDTO> generateMessageDTOList(Integer id, String text) {
        return List.of(generateMessageDTO(id, text), generateMessageDTO(ENTITY_FIELD_ID, ENTITY_FIELD_TEXT));
    }

    private EntityField generateEntityField(MessageDTO messageDTO) {
        return EntityField.builder()
                .field(messageDTO.getText())
                .entityId(messageDTO.getId())
                .build();
    }

    private List<EntityField> generateEntityFieldList(List<MessageDTO> messageDTOs) {
        return messageDTOs.stream()
                .map(messageDTO -> EntityField.builder()
                        .field(messageDTO.getText())
                        .entityId(messageDTO.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private ValidatorError createValidatorError(ErrorType errorType, EntityField entityField) {
        return ValidatorError.builder()
                .message(errorType.getError())
                .field(generateType().getFieldName())
                .entityId(entityField.getEntityId())
                .build();
    }

    private FieldType generateType() {
        return FieldType.TEXT;
    }
}
