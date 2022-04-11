package com.speaker.utils;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.CityName;
import com.speaker.entities.CountryName;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class AccountDTOGenerator {
    private final static Random RANDOM = new Random();

    public static AccountDTO generateAccountDTO(String country, CountryName countryName, String city, CityName cityName) {
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
                    .country(getCountry(countryName, city, cityName))
                    .build();
        }
    }

    public static CountryDTO getCountry(CountryName countryName, String city, CityName cityName) {
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
                    .cityDTO(getCity(cityName))
                    .build();
        }
    }

    public static CityDTO getCity(CityName cityName) {
        return CityDTO.builder()
                .id(2)
                .name(cityName)
                .build();
    }
}
