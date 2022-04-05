package com.speaker.controller;


import com.speaker.dto.AccountDTO;
import com.speaker.entities.Account;
import com.speaker.service.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {
    private final AccountService accountService;


    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(accountService.create(accountDTO));
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("{accountId}")
    public List<Account> getAllFriendsById(@PathVariable int accountId) {
        return accountService.findAllFriendsByAccountId(accountId);
    }
}
