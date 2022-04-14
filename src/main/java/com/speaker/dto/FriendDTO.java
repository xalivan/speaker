package com.speaker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDTO {
    private final String accountFirstLastNames;
    private final String friendFirstLastNames;
}
