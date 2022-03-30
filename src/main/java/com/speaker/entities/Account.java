package com.speaker.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final int countryId;
    private final int cityId;
}
