package com.speaker.convertors;


import com.speaker.entities.Friend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendConverter {
    public Friend convertToFriend(int accountId, int friendId) {
        return Friend.builder()
                .accountId(accountId)
                .friendAccountId(friendId)
                .build();

    }
}