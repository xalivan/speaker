package com.speaker.repository;

import com.speaker.entities.Account;
import com.speaker.entities.Country;
import com.speaker.entities.Friend;
import com.speaker.repository.mappers.AccountMapper;
import com.speaker.repository.mappers.CountryMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    private final CountryMapper countryMapper;
    private final MessageRepository messageRepository;

    @Override
    public Optional<Integer> findAccountIdByNameAndLastName(String name, String lastName) {
        return dsl.select(ACCOUNT.ID)
                .from(ACCOUNT)
                .where(ACCOUNT.NAME.equal(name).and(ACCOUNT.LAST_NAME.eq(lastName)))
                .fetchOptionalInto(Integer.class);
    }

    @Override
    public int addFriend(Friend friend) {
        return dsl.insertInto(FRIENDS, FRIENDS.ID, FRIENDS.ACCOUNT_ID, FRIENDS.FRIEND_ACCOUNT_ID)
                .values(friend.getId(), friend.getAccountId(), friend.getFriendAccountId())
                .execute();
    }

    @Override
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
                .peek(friends -> friends.setFriends(findAllFriendsByAccountId(friends.getId())))
                .peek(account -> account.setMassages(messageRepository.findAllByAccountId(account.getId())))
                .collect(toList());
    }

    @Override
    public List<Country> findAllCountryAndCity() {
        return dsl.select(COUNTRY.NAME, COUNTRY.ID,
                        CITY.NAME, CITY.ID)
                .from(COUNTRY)
                .leftJoin(CITY).on(COUNTRY.ID.eq(CITY.COUNTRY_ID))
                .fetch()
                .map(countryMapper::mapToCountry);
    }

    @Override
    public int insert(Account account) {
        return dsl.insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.NAME, ACCOUNT.LAST_NAME,
                        ACCOUNT.AGE, ACCOUNT.COUNTRY_ID, ACCOUNT.CITY_ID)
                .values(account.getId(), account.getName(), account.getLastName(),
                        account.getAge(), account.getCountry().getId(), account.getCountry().getCity().getId())
                .execute();
    }
}
