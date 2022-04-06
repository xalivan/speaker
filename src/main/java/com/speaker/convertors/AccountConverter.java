package com.speaker.convertors;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
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


    public Account convertToAccount(AccountDTO accountDTO) {
        return Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .country(convertToCountry(accountDTO.getCountry()))
                .build();
    }

    public Country convertToCountry(CountryDTO countryDTO) {
        return Country.builder()
                .id(getCountryId(countryDTO))
                .name(countryDTO.getName())
                .city(convertToCity(countryDTO.getCityDTO()))
                .build();
    }

    public City convertToCity(CityDTO cityDTO) {
        return City.builder()
                .id(getCityId(cityDTO))
                .name(cityDTO.getName())
                .build();
    }

    private int getCityId(CityDTO cityDTO) {
        Map<CityName, Integer> cityMap = new HashMap<>();
        cityMap.put(CityName.KYIV, 1);
        cityMap.put(CityName.NEW_YORK, 2);
        return cityMap.get(cityDTO.getName());
    }

    private int getCountryId(CountryDTO countryDTO) {
        Map<CountryName, Integer> countryMap = new HashMap<>();
        countryMap.put(CountryName.UKRAINE, 1);
        countryMap.put(CountryName.USA, 2);
        return countryMap.get(countryDTO.getName());
    }
}
