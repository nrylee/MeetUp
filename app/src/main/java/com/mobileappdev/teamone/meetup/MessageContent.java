package com.mobileappdev.teamone.meetup;

import com.mobileappdev.teamone.meetup.DbRepository.Repository;

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

    public static List<MessageItem> getItems(Integer chat_id) {
        List<MessageItem> list = new ArrayList<>();
        List<Repository.ChatMessage> chatMessages = (new Repository()).ListMessagesForChat(chat_id);

        for(Repository.ChatMessage chatMessage:chatMessages) {
            ChatMessage msg = new ChatMessage(
                    chatMessage.chatmessage_message,
                    chatMessage.chatmessage_user_name,
                    chatMessage.chatmessage_date
            );
            list.add(msg);
        }

        return list;
    }
}
