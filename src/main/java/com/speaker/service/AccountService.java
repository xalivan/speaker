package com.speaker.service;

import com.speaker.dto.AccountDTO;
import com.speaker.entities.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAll();

    List<Account> findAllFriendsByAccountId(int accountId);

    boolean create(AccountDTO account);
}
