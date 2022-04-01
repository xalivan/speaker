package com.speaker.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel
@Data
public class Message {
    private final int id;
    private final int fromAccountId;
    private final String text;
    private final LocalDateTime date;
    private final Status status;
}
