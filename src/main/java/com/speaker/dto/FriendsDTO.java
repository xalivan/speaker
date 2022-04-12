package com.speaker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendsDTO {
    private final String nameUser1;
    private final String nameUser2;
}
