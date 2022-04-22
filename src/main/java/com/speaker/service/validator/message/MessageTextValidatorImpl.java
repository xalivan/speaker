package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class MessageTextValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {
    @Override
    public ValidatorError validate(EntityField entityField) {
        Object fieldName = entityField.getField();
        if (isNull(fieldName)) {
            return createValidatorError(ErrorType.EMPTY, entityField);
        }
        if (fieldName.equals("")) {
            return createValidatorError(ErrorType.NOT_VALID, entityField);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return EntityField.builder()
                .field(messageDTO.getText())
                .entityId(messageDTO.getId())
                .build();
    }

    @Override
    protected FieldType getType() {
        return FieldType.TEXT;
    }
}
