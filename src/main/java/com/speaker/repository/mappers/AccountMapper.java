package com.speaker.repository.mappers;

import com.speaker.entities.Account;
import lombok.RequiredArgsConstructor;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import static com.speaker.db.jooq.generated.tables.Account.ACCOUNT;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final CountryMapper countryMapper;

    public Account mapToAccount(Record record) {
        return Account.builder()
                .id(record.get(ACCOUNT.ID))
                .name(record.get(ACCOUNT.NAME))
                .lastName(record.get(ACCOUNT.LAST_NAME))
                .age(record.get(ACCOUNT.AGE))
                .country(countryMapper.mapToCountry(record))
                .build();
    }


}
