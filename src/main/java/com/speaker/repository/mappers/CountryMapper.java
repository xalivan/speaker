package com.speaker.repository.mappers;

import com.speaker.entities.City;
import com.speaker.entities.CityName;
import com.speaker.entities.Country;
import com.speaker.entities.CountryName;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.speaker.db.jooq.generated.tables.City.CITY;
import static com.speaker.db.jooq.generated.tables.Country.COUNTRY;

@Component
public class CountryMapper {
    public Country mapToCountry(Record record) {
        return Country.builder()
                .id(record.get(COUNTRY.ID))
                .name(CountryName.valueOf(record.get(COUNTRY.NAME)))
                .city(City.builder()
                        .id(record.get(CITY.ID))
                        .name(CityName.valueOf(record.get(CITY.NAME)))
                        .build())
                .build();
    }
}
