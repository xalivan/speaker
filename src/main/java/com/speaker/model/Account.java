package com.speaker.model;

import lombok.Value;

@Value
public class Account {
    int id;
    String name;
    String lastName;
    int age;
    int countryId;
    int cityId;
}
