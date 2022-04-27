package com.speaker.entities;

import lombok.*;

@Data
@Builder
public class EntityField {
    private final Object field;
    private final Integer entityId;
}
