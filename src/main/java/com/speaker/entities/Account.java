package com.speaker.entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Account {
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final Country country;
    private final List<Account> friends;
    private final List<Message> massages;
}
