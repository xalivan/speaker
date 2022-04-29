package com.speaker.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.validator.AbstractValidator;
import com.speaker.validator.Validator;
import com.speaker.validator.type.ErrorType;
import com.speaker.validator.type.FieldType;
import org.springframework.stereotype.Component;

@Component
public class MessageFromAccountIdValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {

    @Override
    public ValidatorError validate(EntityField entityField) {
        Integer field = (Integer) entityField.getField();
        if (field <= 0) {
            return createValidatorError(ErrorType.NOT_VALID, entityField);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return EntityField.builder()
                .field(messageDTO.getFromAccountId())
                .entityId(messageDTO.getId())
                .build();
    }

    @Override
    protected FieldType getType() {
        return FieldType.FROM_ACCOUNT_ID;
    }
}
