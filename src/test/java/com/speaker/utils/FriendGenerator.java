package com.speaker.utils;

import com.speaker.entities.Friend;

public class FriendGenerator {
    public static Friend generateFriend(int accountId, int friendId){
        return Friend.builder()
                .accountId(accountId)
                .friendAccountId(friendId)
                .build();

    }
}
