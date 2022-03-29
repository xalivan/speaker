package com.speaker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final int countryId;
    private final int cityId;
}
