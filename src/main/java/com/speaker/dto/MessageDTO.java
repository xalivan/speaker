package com.speaker.dto;

import com.speaker.entities.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MessageDTO {
    private final int id;
    private final int fromAccountId;
    private final int toAccountId;
    private final String text;
    private final LocalDateTime date;
    private final Status status;
    private final String fromAccountNames;
    private final String toAccountNames;
}
