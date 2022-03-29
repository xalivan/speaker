package com.speaker.service;

import com.speaker.dto.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAll();

    int create(AccountDTO account);
}
