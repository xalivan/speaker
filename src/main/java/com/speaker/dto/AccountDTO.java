package com.speaker.dto;

import com.speaker.entities.Account;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDTO {

    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final CountryDTO country;
    private final List<Account> friends;
    private final List<MessageDTO> massages;
}
