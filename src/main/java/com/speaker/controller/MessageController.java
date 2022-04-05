package com.speaker.controller;

import com.speaker.entities.Message;
import com.speaker.service.MessageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Integer> createMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageService.create(message));
    }
    @GetMapping
    public ResponseEntity<List<Message>> getMessagesByAccountId(int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByAccountId(accountId));
    }
}
