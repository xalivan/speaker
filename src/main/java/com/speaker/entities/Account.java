package com.speaker.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final Country country;
    private List<Account> friends;
    private List<Message> massages;
}
