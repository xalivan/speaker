package com.speaker.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.entities.Status;
import com.speaker.validator.AbstractValidator;
import com.speaker.validator.Validator;
import com.speaker.validator.type.ErrorType;
import com.speaker.validator.type.FieldType;
import org.springframework.stereotype.Component;

@Component
public class MessageStatusValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {

    @Override
    public ValidatorError validate(EntityField entityField) {
        if (!Status.NEW.equals(entityField.getField())) {
            return createValidatorError(ErrorType.NOT_FOUND, entityField);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return EntityField.builder()
                .field(messageDTO.getStatus())
                .entityId(messageDTO.getId())
                .build();
    }

    @Override
    protected FieldType getType() {
        return FieldType.STATUS;
    }
}
