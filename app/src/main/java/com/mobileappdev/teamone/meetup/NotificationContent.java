package com.mobileappdev.teamone.meetup;

import java.util.ArrayList;
import java.util.List;

public class NotificationContent {

    public static List<NotificationItem> getItem() {
        List<NotificationItem> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Notification("Notification"));
        }
        return list;
    }

}
