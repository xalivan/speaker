package com.speaker.validator.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FieldType {
    STATUS("status"),
    FROM_ACCOUNT_ID("fromAccountId"),
    TO_ACCOUNT_ID("toAccountId"),
    TEXT("text"),
    DATE("date"),
    FROM_ACCOUNT_NAMES("fromAccountNames"),
    TO_ACCOUNT_NAMES("toAccountNames");
    private final String fieldName;
}
