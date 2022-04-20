package com.speaker.service.validator.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {
    EMPTY(" :is empty"),
    NOT_FOUND(" :not found"),
    NOT_VALID(" :is not valid");
    private final String error;
}
