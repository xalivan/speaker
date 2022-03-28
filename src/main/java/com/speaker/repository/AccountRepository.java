package com.speaker.repository;

import com.speaker.entities.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    int insert(Account account);
}
