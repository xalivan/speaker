package com.speaker.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class AccountDTO {
    private final int id;
    private final String name;
    private final String lastName;
    private final int age;
    private final CountryDTO country;
    private final List<AccountDTO> friends;
    private final List<MessageDTO> massages;
}
