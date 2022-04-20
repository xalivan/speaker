package com.speaker.service.validator;

import com.speaker.dto.ValidatorError;

import java.util.function.Supplier;

public interface Validator<T, E> {
    ValidatorError validate(E entity);

    Supplier<E> getField(T t);
}
