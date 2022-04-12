package com.speaker.utils;

import com.speaker.entities.*;

import java.util.Random;

public class AccountGenerator {
    private final static Random RANDOM = new Random();

    public static Account generateAccount(Country country) {
        return Account.builder()
                .id(RANDOM.nextInt(10))
                .name("Ivan")
                .lastName("Mazur")
                .age(RANDOM.nextInt(10))
                .country(generateCountry(country.getName(), country.getCity()))
                .build();
    }

    public static Country generateCountry(CountryName countryName, City city) {
        return Country.builder()
                .id(1)
                .name(countryName)
                .city(generateCity(city.getName()))
                .build();
    }

    public static City generateCity(CityName cityName) {
        return City.builder()
                .id(2)
                .name(cityName)
                .build();
    }
}
