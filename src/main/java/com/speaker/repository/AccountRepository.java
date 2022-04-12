package com.speaker.repository;

import com.speaker.entities.Account;
import com.speaker.entities.Country;
import com.speaker.entities.Friends;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    List<Account> findAllFriendsByAccountId(int accountId);

    List<Country> findAllCountryAndCity();

    int insert(Account account);

    int addFriends(Friends friends);

    Optional<Account> findAccountByNameAndLastName(String name, String lastName);
}
