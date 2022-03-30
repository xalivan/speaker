package com.speaker.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friends {
    private final int id;
    private final int accountId;
    private final int friendAccountId;
}
