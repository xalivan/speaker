package com.speaker.repository;

import com.speaker.entities.Account;
import com.speaker.entities.Country;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    List<Account> findAllFriendsByAccountId(int accountId);

    List<Country> findAllCountryAndCity();

    int insert(Account account);
}
