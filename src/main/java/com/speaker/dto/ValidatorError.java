package com.speaker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatorError {
    private final String message;
    private final String field;
    private final Integer entityId;
}

