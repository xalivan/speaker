package com.speaker.convertors;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.Account;
import com.speaker.entities.City;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountConverter {
    private final MessageConvertor messageConvertor;

    public AccountDTO convertToAccountDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .name(account.getName())
                .lastName(account.getLastName())
                .age(account.getAge())
                .country(CountryDTO.builder()
                        .id(account.getCountry().getId())
                        .name(account.getCountry().getName())
                        .city(City.builder()
                                .id(account.getCountry().getCity().getId())
                                .name(account.getCountry().getCity().getName())
                                .build())
                        .build())
                .massages(account.getMassages().stream()
                        .map(messageConvertor::convertTo)
                        .collect(Collectors.toList()))
                .build();
    }

    public Account convertToAccount(AccountDTO accountDTO) {
        return Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .build();
    }
}
