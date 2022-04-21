package com.speaker.service.validator.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorType {
    EMPTY("Field is empty"),
    NOT_FOUND("Field not found"),
    NOT_VALID("Field is not valid");
    private final String error;
}
