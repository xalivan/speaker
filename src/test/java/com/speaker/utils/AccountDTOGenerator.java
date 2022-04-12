package com.speaker.utils;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.CityName;
import com.speaker.entities.CountryName;

import java.util.Optional;
import java.util.Random;

public class AccountDTOGenerator {
    private final static Random RANDOM = new Random();

    public static AccountDTO generateAccountDTO(CountryDTO country) {
        return AccountDTO.builder()
                .id(RANDOM.nextInt(10))
                .name("Ivan")
                .lastName("Mazur")
                .age(RANDOM.nextInt(10))
                .country(Optional.ofNullable(country)
                        .map(countryDTO -> generateCountryDTO(countryDTO.getName(), countryDTO.getCityDTO()))
                        .orElse(null))
                .build();
    }

    public static CountryDTO generateCountryDTO(CountryName countryName, CityDTO city) {
        return CountryDTO.builder()
                .id(1)
                .name(countryName)
                .cityDTO(Optional.ofNullable(city)
                        .map(cityDTO -> generateCityDTO(city.getName()))
                        .orElse(null))
                .build();

    }

    public static CityDTO generateCityDTO(CityName cityName) {
        return CityDTO.builder()
                .id(2)
                .name(cityName)
                .build();
    }
}
