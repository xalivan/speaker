package com.speaker.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {
    private final int id;
    private final int fromAccountId;
    private final int toAccountId;
    private final String text;
    private final LocalDateTime date;
    private final Status status;
}
