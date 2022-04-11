package com.speaker.convertors;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.Account;
import com.speaker.entities.City;
import com.speaker.entities.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
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
                                .map(messageConvertor::convertTo)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .build();
    }


    public Account convertToAccount(AccountDTO accountDTO, int countryId, int cityId) {
        return Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .country(convertToCountry(accountDTO.getCountry(), countryId, cityId))
                .build();
    }

    public Country convertToCountry(CountryDTO countryDTO, int countryId, int cityId) {
        return Country.builder()
                .id(countryId)
                .name(countryDTO.getName())
                .city(convertToCity(countryDTO.getCityDTO(), cityId))
                .build();
    }

    public City convertToCity(CityDTO cityDTO, int cityId) {
        return City.builder()
                .id(cityId)
                .name(cityDTO.getName())
                .build();
    }


}
