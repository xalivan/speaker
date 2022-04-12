package com.speaker.utils;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.CityName;
import com.speaker.entities.CountryName;

import java.util.Random;

public class AccountDTOGenerator {
    private final static Random RANDOM = new Random();

    public static AccountDTO generateAccountDTO(CountryDTO country) {
        if (country == null) {
            return AccountDTO.builder()
                    .id(RANDOM.nextInt(10))
                    .name("Ivan")
                    .lastName("Mazur")
                    .age(RANDOM.nextInt(10))
                    .country(null)
                    .build();
        } else {
            return AccountDTO.builder()
                    .id(RANDOM.nextInt(10))
                    .name("Ivan")
                    .lastName("Mazur")
                    .age(RANDOM.nextInt(10))
                    .country(generateCountryDTO(country.getName(), country.getCityDTO()))
                    .build();
        }
    }

    public static CountryDTO generateCountryDTO(CountryName countryName, CityDTO city) {
        if (city == null) {
            return CountryDTO.builder()
                    .id(1)
                    .name(countryName)
                    .cityDTO(null)
                    .build();
        } else {
            return CountryDTO.builder()
                    .id(1)
                    .name(countryName)
                    .cityDTO(generateCityDTO(city.getName()))
                    .build();
        }
    }

    public static CityDTO generateCityDTO(CityName cityName) {
        return CityDTO.builder()
                .id(2)
                .name(cityName)
                .build();
    }
}
