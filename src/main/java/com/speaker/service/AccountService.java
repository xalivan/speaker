package com.speaker.service;

import com.speaker.entities.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();

    int insert(Account account);
}
