package com.speaker.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.validator.AbstractValidator;
import com.speaker.validator.Validator;
import com.speaker.validator.type.ErrorType;
import com.speaker.validator.type.FieldType;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class MessageTextValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {
    @Override
    public ValidatorError validate(EntityField entityField) {
       String fieldName = (String)entityField.getField();
        if (isNull(fieldName)) {
            return createValidatorError(ErrorType.EMPTY, entityField);
        }
        if (fieldName.trim().equals("")) {
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
    public FieldType getType() {
        return FieldType.TEXT;
    }
}
