package com.speaker.repository;

import com.speaker.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    List<Message> findAllByAccountId(int id);

    Optional<List<Integer>> insert(List<Message> message);
}
