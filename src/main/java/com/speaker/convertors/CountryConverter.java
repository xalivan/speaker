package com.speaker.convertors;

import com.speaker.dto.CityDTO;
import com.speaker.dto.CountryDTO;
import com.speaker.entities.City;
import com.speaker.entities.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter {
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
