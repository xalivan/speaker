package com.speaker.service;

import com.speaker.DTO.AccountDTO;
import com.speaker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    @Override
    public List<AccountDTO> findAll() {
        return accountConverter.convertToAccountDTO(accountRepository.findAll());
    }

    @Override
    public int create(AccountDTO accountDTO) {
        return accountRepository.create(accountConverter.convertToAccount(accountDTO));
    }
}
