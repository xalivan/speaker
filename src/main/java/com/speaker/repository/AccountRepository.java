package com.speaker.repository;

import com.speaker.entities.Account;
import com.speaker.entities.Country;
import com.speaker.entities.Friend;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    List<Account> findAll();

    List<Account> findAllFriendsByAccountId(int accountId);

    List<Country> findAllCountryAndCity();

    int insert(Account account);

    int addFriend(Friend friend);

    Optional<Integer> findAccountIdByNameAndLastName(String name, String lastName);
}
