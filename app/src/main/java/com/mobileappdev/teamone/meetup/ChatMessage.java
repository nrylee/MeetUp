package com.mobileappdev.teamone.meetup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage implements MessageItem {

    private String messageText;
    private String messageUser;
    private Date messageTime;

    public ChatMessage(String messageText, String messageUser, Date messageTime) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        this.messageTime = messageTime;

    }

    @Override
    public String getMessageText() {

        return this.messageText;
    }

    @Override
    public String getMessageUser() {

        return this.messageUser;
    }

    @Override
    public Date getMessageTime() {
        return this.messageTime;
    }

    @Override
    public String getMessageTimeString() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.format(messageTime);
        return dateFormat.format(messageTime);
    }
}
