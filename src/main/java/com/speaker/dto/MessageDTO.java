package com.speaker.dto;

import com.speaker.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO implements BaseEntityDTO {
    private int id;
    private int fromAccountId;
    private int toAccountId;
    private String text;
    private LocalDateTime date;
    private Status status;
    private String fromAccountNames;
    private String toAccountNames;
}
