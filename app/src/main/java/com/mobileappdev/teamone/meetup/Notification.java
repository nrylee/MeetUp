package com.mobileappdev.teamone.meetup;

public class Notification implements NotificationItem {

    private String notification;

    public Notification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }
}
