package com.speaker.repository;

import com.speaker.entities.Account;
import com.speaker.repository.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.speaker.db.jooq.generated.tables.Account.ACCOUNT;
import static com.speaker.db.jooq.generated.tables.City.CITY;
import static com.speaker.db.jooq.generated.tables.Country.COUNTRY;
import static com.speaker.db.jooq.generated.tables.Friends.FRIENDS;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final DSLContext dsl;
    private final AccountMapper accountMapper;
    private final MessageRepository messageRepository;

    @Override
    public List<Account> findAll() {
        return dsl.select(ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.LAST_NAME, ACCOUNT.AGE,
                        COUNTRY.NAME, COUNTRY.ID,
                        CITY.NAME, CITY.ID)
                .from(ACCOUNT)
                .leftJoin(COUNTRY).on(ACCOUNT.COUNTRY_ID.eq(COUNTRY.ID))
                .leftJoin(CITY).on(COUNTRY.ID.eq(CITY.COUNTRY_ID))
                .fetch()
                .map(accountMapper::mapToAccount)
                .stream()
                .peek(friends-> friends.setFriends(findAllFriendsByAccountId(friends.getId())))
                .peek(account -> account.setMassages(messageRepository.findAllByAccountId(account.getId())))
                .collect(toList());
    }

    public List<Account> findAllFriendsByAccountId(int accountId) {
        return dsl.select(ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.LAST_NAME, ACCOUNT.AGE,
                        COUNTRY.NAME, COUNTRY.ID,
                        CITY.NAME, CITY.ID)
                .from(ACCOUNT)
                .leftJoin(COUNTRY).on(ACCOUNT.COUNTRY_ID.eq(COUNTRY.ID))
                .leftJoin(CITY).on(COUNTRY.ID.eq(CITY.COUNTRY_ID))
                .leftJoin(FRIENDS).on(ACCOUNT.ID.eq(FRIENDS.FRIEND_ACCOUNT_ID))
                .where(FRIENDS.ACCOUNT_ID.eq(accountId))
                .fetch()
                .map(accountMapper::mapToAccount);
    }

    @Override
    public int insert(Account account) {
        return dsl.insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.LAST_NAME, ACCOUNT.AGE)
                .values(account.getId(), account.getName(), account.getLastName(), account.getAge())
                .execute();
    }
}
