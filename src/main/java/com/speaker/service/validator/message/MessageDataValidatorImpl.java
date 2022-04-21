package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.util.Objects.isNull;

@Component
public class MessageDataValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {
    private final long CURRENT_TIME = System.currentTimeMillis();

    @Override
    public ValidatorError validate(EntityField entityField) {
        LocalDateTime field = (LocalDateTime) entityField.getField();
        if (isNull(field)) {
            return createValidatorError(ErrorType.EMPTY);
        }
        if (checkIsFutureTime(field)) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getDate());
    }

    public boolean checkIsFutureTime(LocalDateTime localDateTime) {
        return CURRENT_TIME < localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    protected FieldType getType() {
        return FieldType.DATE;
    }
}
