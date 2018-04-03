package com.mobileappdev.teamone.meetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageContent {
    public static List<MessageItem> getItems() {
        List<MessageItem> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new ChatMessage(
                    "Example Message " + i,
                    "John",
                     new Date()
            ));
        }
        return list;
     }
}
