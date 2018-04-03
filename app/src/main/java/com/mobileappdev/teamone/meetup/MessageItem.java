package com.mobileappdev.teamone.meetup;

import java.util.Date;

public interface MessageItem {
    String getMessageText();
    String getMessageUser();
    Date getMessageTime();
    String getMessageTimeString();
}
