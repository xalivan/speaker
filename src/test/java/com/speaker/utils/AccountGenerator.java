package com.speaker.utils;

import com.speaker.entities.*;
import lombok.RequiredArgsConstructor;

import java.util.Random;

@RequiredArgsConstructor
public class AccountGenerator {
    private final static Random RANDOM = new Random();

    public static Account generateAccount(String country, CountryName countryName, String city, CityName cityName) {
        if (country == null) {
            return Account.builder()
                    .id(RANDOM.nextInt(10))
                    .name("Ivan")
                    .lastName("Mazur")
                    .age(RANDOM.nextInt(10))
                    .country(null)
                    .build();
        } else {
            return Account.builder()
                    .id(RANDOM.nextInt(10))
                    .name("Ivan")
                    .lastName("Mazur")
                    .age(RANDOM.nextInt(10))
                    .country(getCountry(countryName, city, cityName))
                    .build();
        }
    }

    public static Country getCountry(CountryName countryName, String city, CityName cityName) {
        if (city == null) {
            return Country.builder()
                    .id(1)
                    .name(countryName)
                    .city(null)
                    .build();
        } else {
            return Country.builder()
                    .id(1)
                    .name(countryName)
                    .city(getCity(cityName))
                    .build();
        }
    }

    public static City getCity(CityName cityName) {
        return City.builder()
                .id(2)
                .name(cityName)
                .build();
    }
}
