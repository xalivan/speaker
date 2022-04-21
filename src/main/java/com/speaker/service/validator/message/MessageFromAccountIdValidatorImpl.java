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
public class MessageFromAccountIdValidatorImpl extends AbstractValidator implements Validator<MessageDTO> {

    @Override
    public ValidatorError validate(EntityField entityField) {
        if (isNull(entityField.getField().toString())) {
            return createValidatorError(ErrorType.EMPTY);
        }
        if (!parseToInt(entityField)) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        if ((int) entityField.getField() < 0) {
            return createValidatorError(ErrorType.NOT_VALID);
        }
        return null;
    }

    @Override
    public EntityField getField(MessageDTO messageDTO) {
        return new EntityField(messageDTO.getFromAccountId());
    }


    @Override
    protected FieldType getType() {
        return FieldType.FROM_ACCOUNT_ID;
    }

    private boolean parseToInt(EntityField entityField) {
        String isInteger = entityField.getField().toString();
        return isInteger.matches("[+]?\\d+");
    }
}
