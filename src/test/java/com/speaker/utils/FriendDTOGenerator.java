package com.speaker.utils;

import com.speaker.dto.FriendDTO;


public class FriendDTOGenerator {
    public static FriendDTO generateFriendDTO(String accountName, String friendName){
        return FriendDTO.builder()
                .accountFirstLastNames(accountName)
                .friendFirstLastNames(friendName)
                .build();
    }

}
