package com.speaker.service;

import com.speaker.dto.AccountDTO;
import com.speaker.dto.FriendDTO;
import com.speaker.entities.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAll();

    List<Account> findAllFriendsByAccountId(int accountId);

    Response create(AccountDTO account);

    Response addFriend(FriendDTO friendDTO);
}
