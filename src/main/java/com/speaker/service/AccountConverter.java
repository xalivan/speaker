package com.speaker.service;

import com.speaker.DTO.AccountDTO;
import com.speaker.entities.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {
    public List<AccountDTO> convertToAccountDTO(List<Account> account) {
        return account.stream()
                .map(a -> new AccountDTO(a.getName(), a.getLastName(), a.getAge(), a.getCountryId(), a.getCityId()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Account convertToAccount(AccountDTO accountDTO) {
        return Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .countryId(accountDTO.getCountryId())
                .cityId(accountDTO.getCityId())
                .build();
    }
}
