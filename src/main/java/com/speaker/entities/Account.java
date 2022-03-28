package com.speaker.entities;

import lombok.Data;

@Data
public class Account {
    final int id;
    final String name;
    final String lastName;
    final int age;
    final int countryId;
    final int cityId;
}
