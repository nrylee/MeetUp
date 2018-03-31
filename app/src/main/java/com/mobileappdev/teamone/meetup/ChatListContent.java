package com.mobileappdev.teamone.meetup;

import com.mobileappdev.teamone.meetup.ChatListEventItem;
import com.mobileappdev.teamone.meetup.ChatListItem;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ChatListContent {
    public static List<ChatListItem> getItems() {
        List<ChatListItem> _list = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            _list.add(new ChatListEventItem(
                    i,
                    "Title of Event " + i,
                    Double.valueOf(Math.random() * 50).intValue(),
                    Math.random() * 10000,
                    new Date()
            ));
        }
        return _list;
    }


}
