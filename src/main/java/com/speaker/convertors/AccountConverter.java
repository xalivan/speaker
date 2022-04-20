package com.speaker.convertors;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountConverter {
    private final MessageConvertor messageConvertor;
    private final CountryConverter countryConverter;

    public AccountDTO convertToAccountDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .name(account.getName())
                .lastName(account.getLastName())
                .age(account.getAge())
                .country(CountryDTO.builder()
                        .id(account.getCountry().getId())
                        .name(account.getCountry().getName())
                        .cityDTO(CityDTO.builder()
                                .id(account.getCountry().getCity().getId())
                                .name(account.getCountry().getCity().getName())
                                .build())
                        .build())
                .friends(Optional.ofNullable(account.getFriends())
                        .map(friends -> friends.stream()
                                .map(this::convertToAccountDTO)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .massages(Optional.ofNullable(account.getMassages())
                        .map(message -> message.stream()
                                .map(messageConvertor::convertToMessageDTO)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .build();
    }


    public Account convertToAccount(AccountDTO accountDTO, int countryId, int cityId) {
        return Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .country(countryConverter.convertToCountry(accountDTO.getCountry(), countryId, cityId))
                .build();
    }


}
