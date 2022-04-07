package com.speaker.convertors;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.Account;
import com.speaker.entities.City;
import com.speaker.entities.Country;
import com.speaker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountConverter {
    private final MessageConvertor messageConvertor;
    private final AccountRepository accountRepository;

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
                .country(convertToCountry(accountDTO.getCountry(), accountDTO))
                .build();
    }

    public Country convertToCountry(CountryDTO countryDTO, AccountDTO accountDTO) {
        return Country.builder()
                .id(getCountryId(accountDTO))
                .name(countryDTO.getName())
                .city(convertToCity(countryDTO.getCityDTO(), accountDTO))
                .build();
    }

    public City convertToCity(CityDTO cityDTO, AccountDTO accountDTO) {
        return City.builder()
                .id(getCityId(accountDTO))
                .name(cityDTO.getName())
                .build();
    }

    private int getCityId(AccountDTO accountDTO) {
        String cityName = accountDTO.getCountry().getCityDTO().getName().name();
        return allCountryAndCity().stream()
                .filter(country -> country.getCity().getName().name().equals(cityName))
                .map(country -> country.getCity().getId()).findFirst().get();
    }

    private int getCountryId(AccountDTO accountDTO) {
        String countryName = accountDTO.getCountry().getName().name();
        return allCountryAndCity().stream()
                .filter(country -> country.getName().name().equals(countryName))
                .map(Country::getId).findFirst().get();
    }

    private List<Country> allCountryAndCity() {
        return accountRepository.findAllCountryAndCity();
    }
}
