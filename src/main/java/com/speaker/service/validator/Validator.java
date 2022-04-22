package com.speaker.service.validator;

import com.speaker.dto.ValidatorError;
import com.speaker.entities.EntityField;

import java.util.List;
import java.util.Set;

public interface Validator<T> {
    ValidatorError validate(EntityField field);

    List<ValidatorError> validateList(List<EntityField> entityFields);

    EntityField getField(T entity);

    List<EntityField> getFields(List<T> entity);
}
