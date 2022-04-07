package com.speaker.service;

import com.speaker.convertors.AccountConverter;
import com.speaker.dto.AccountDTO;
import com.speaker.entities.Account;
import com.speaker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository.findAll().stream()
                .map(accountConverter::convertToAccountDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllFriendsByAccountId(int accountId) {
        return accountRepository.findAllFriendsByAccountId(accountId);
    }

    @Override
    public boolean create(AccountDTO accountDTO) {
        return accountRepository.insert(accountConverter.convertToAccount(accountDTO)) > 0;
    }
}
