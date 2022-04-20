package com.speaker.controller;

import com.speaker.dto.MessageDTO;
import com.speaker.dto.ValidatorError;
import com.speaker.entities.Message;
import com.speaker.service.MessageService;
import com.speaker.service.Response;
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

    @GetMapping
    public ResponseEntity<List<Message>> getMessagesByAccountId(int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByAccountId(accountId));
    }

    @PostMapping("add/messages")
    public ResponseEntity<List<ValidatorError>> addFriendMassages(@RequestBody List<MessageDTO> messageDTO) {
        return ResponseEntity.ok(messageService.addMessage(messageDTO));
    }
}
