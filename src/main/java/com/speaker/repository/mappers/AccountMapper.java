package com.speaker.repository.mappers;

import com.speaker.entities.*;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.speaker.db.jooq.generated.tables.Account.ACCOUNT;
import static com.speaker.db.jooq.generated.tables.City.CITY;
import static com.speaker.db.jooq.generated.tables.Country.COUNTRY;

@Component
public class AccountMapper {
    public Account mapToAccount(Record record) {
        return Account.builder()
                .id(record.get(ACCOUNT.ID))
                .name(record.get(ACCOUNT.NAME))
                .lastName(record.get(ACCOUNT.LAST_NAME))
                .age(record.get(ACCOUNT.AGE))
                .country(Country.builder()
                        .id(record.get(COUNTRY.ID))
                        .name(CountryName.valueOf(record.get(COUNTRY.NAME)))
                        .city(City.builder()
                                .id(record.get(CITY.ID))
                                .name(CityName.valueOf(record.get(CITY.NAME)))
                                .build())
                        .build())
                .build();
    }
}
