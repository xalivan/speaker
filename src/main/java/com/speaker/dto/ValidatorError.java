package com.speaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class ValidatorError {
    private final String message;
    private final String field;
}

