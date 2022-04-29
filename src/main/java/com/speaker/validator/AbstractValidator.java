package com.speaker.validator;

import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;
import com.speaker.validator.type.ErrorType;
import com.speaker.validator.type.FieldType;

public abstract class AbstractValidator {

    protected ValidatorError createValidatorError(ErrorType errorType, EntityField entityField) {
        return ValidatorError.builder()
                .message(errorType.getError())
                .field(getType().getFieldName())
                .entityId(entityField.getEntityId())
                .build();
    }

    protected abstract FieldType getType();
}
