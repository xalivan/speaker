package com.speaker.entities;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private final int id;
    private final int fromAccountId;
    private final String text;
    private final LocalDateTime date;
    private final Status status;
}
