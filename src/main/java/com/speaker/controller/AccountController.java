package com.speaker.controller;

import com.speaker.DTO.AccountDTO;
import com.speaker.entities.Account;
import com.speaker.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody AccountDTO accountDTO) {
        Account account = Account.builder()
                .name(accountDTO.getName())
                .lastName(accountDTO.getLastName())
                .age(accountDTO.getAge())
                .countryId(accountDTO.getCountryId())
                .cityId(accountDTO.getCityId())
                .build();
        return ResponseEntity.ok(accountService.insert(account));
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }
}
