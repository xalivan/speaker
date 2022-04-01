package com.speaker.convertors;

import com.speaker.dto.AccountDTO;
import com.speaker.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {
    public AccountDTO convertToAccountDTO(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .name(account.getName())
                .lastName(account.getLastName())
                .age(account.getAge())
                .build();
    }

    public Account convertToAccount(AccountDTO accountDTO) {
        return Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .build();
    }
}
