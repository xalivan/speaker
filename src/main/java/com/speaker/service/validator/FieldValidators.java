package com.speaker.service.validator;

import com.speaker.dto.ValidatorError;

import java.util.List;
import java.util.Set;

public interface FieldValidators<T> {
    List<ValidatorError> validate(T entity);

    List<List<ValidatorError>> validate(List<T> entityFields);
}
