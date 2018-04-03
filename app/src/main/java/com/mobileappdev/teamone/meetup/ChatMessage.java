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
        messageTime = new Date();

    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public Date getMessageTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.format(messageTime);
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }
}
