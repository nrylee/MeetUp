package com.mobileappdev.teamone.meetup;

import com.mobileappdev.teamone.meetup.ChatListEventItem;
import com.mobileappdev.teamone.meetup.ChatListItem;
import com.mobileappdev.teamone.meetup.DbRepository.Repository;

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

    public static List<ChatListItem> getItemsForUser(Integer user_id) {
        List<ChatListItem> _list = new ArrayList<>();
        List<Repository.Chat> chats = (new Repository()).ListAllChatsForUser(user_id);

        for(Repository.Chat chat:chats) {
            ChatListItem chatListItem;
            if (chat.event == null) {

            }
            else {
                chatListItem = new ChatListEventItem(
                        chat.chat_id,
                        chat.event.event_name,
                        chat.user_count,
                        Math.random() * 10000,
                        chat.event.event_start
                );
                _list.add(chatListItem);
            }
        }
        return _list;
    }


}
