package com.speaker.validator;

import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;

public interface Validator<T> {
    ValidatorError validate(EntityField field);

    EntityField getField(T entity);
}
