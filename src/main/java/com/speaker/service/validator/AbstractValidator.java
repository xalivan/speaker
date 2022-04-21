package com.speaker.service.validator;

import com.speaker.dto.ValidatorError;
import com.speaker.service.validator.type.ErrorType;
import com.speaker.service.validator.type.FieldType;

public abstract class AbstractValidator {

    protected ValidatorError createValidatorError(ErrorType errorType) {
        return ValidatorError.builder()
                .message(errorType.getError())
                .field(getType().getFieldName())
                .build();
    }

    protected abstract FieldType getType();
}
