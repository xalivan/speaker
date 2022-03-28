package com.speaker.controller;

import com.speaker.entities.Account;
import com.speaker.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Integer> insert(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.insert(account));
    }

    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }
}
