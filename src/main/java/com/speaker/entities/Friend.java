package com.speaker.entities;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class Friend extends Account {
    private final int parentId;

    public Friend(int id, String name, String lastName, int age, Country country,
                  List<Account> friends, List<Message> massages,
                  int parentId) {
        super(id, name, lastName, age, country, friends, massages);
        this.parentId = parentId;
    }
}
