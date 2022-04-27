package com.speaker.service.validator.message;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.service.validator.AbstractValidator;
import com.speaker.service.validator.Validator;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;
import org.springframework.stereotype.Component;

@Component
public class MessageToAccountIdValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {

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
                .field(messageDTO.getToAccountId())
                .entityId(messageDTO.getId())
                .build();
    }

    @Override
    protected FieldType getType() {
        return FieldType.TO_ACCOUNT_ID;
    }
}
