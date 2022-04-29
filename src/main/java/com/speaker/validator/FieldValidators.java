package com.speaker.validator;

import com.speaker.dto.ValidatorError;

import java.util.List;

public interface FieldValidators<T> {
    List<ValidatorError> validate(T entity);

    List<ValidatorError> validate(List<T> entityFields);
}
