package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.entities.Status;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MessageStatusValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {

    @Override
    public ValidatorError validate(EntityField entityField) {
        if (!Objects.equals(entityField.getField().toString(), Status.NEW.name())) {
            return createValidatorError(ErrorType.NOT_FOUND);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getStatus());
    }

    @Override
    protected FieldType getType() {
        return FieldType.STATUS;
    }
}
