package com.speaker.dto;

import com.speaker.entities.Status;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO implements BaseEntityDTO, Serializable {
    private int id;
    private int fromAccountId;
    private int toAccountId;
    private String text;
    private LocalDateTime date;
    private Status status;
    private String fromAccountNames;
    private String toAccountNames;
}
