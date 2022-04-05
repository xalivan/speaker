package com.speaker.repository;

import com.speaker.entities.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    List<Account> findAllFriendsByAccountId(int accountId);

    int insert(Account account);
}
