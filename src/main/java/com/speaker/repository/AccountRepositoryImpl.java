package com.speaker.repository;

import com.speaker.entities.Account;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.speaker.db.jooq.generated.tables.Account.ACCOUNT;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final DSLContext dsl;

    public AccountRepositoryImpl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<Account> findAll() {
        return dsl.select(ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.LAST_NAME, ACCOUNT.AGE, ACCOUNT.COUNTRY_ID, ACCOUNT.CITY_ID)
                .from(ACCOUNT)
                .fetchInto(Account.class);
    }

    @Override
    public int insert(Account account) {
        return dsl.insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.LAST_NAME, ACCOUNT.AGE, ACCOUNT.COUNTRY_ID, ACCOUNT.CITY_ID)
                .values(account.getId(), account.getName(), account.getLastName(), account.getAge(), account.getCountryId(), account.getCityId())
                .execute();
    }
}
