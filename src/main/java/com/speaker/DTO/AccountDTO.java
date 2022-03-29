package com.speaker.DTO;

import lombok.Data;

@Data
public class AccountDTO {
    private final String name;
    private final String lastName;
    private final int age;
    private final int countryId;
    private final int cityId;
}
