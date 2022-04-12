package com.speaker.convertors;


import com.speaker.entities.Friends;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendConverter {
    public Friends convertToFriends(int accountId1, int accountId2) {
        return Friends.builder()
                .accountId(accountId1)
                .friendAccountId(accountId2)
                .build();

    }
}