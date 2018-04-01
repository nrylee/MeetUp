package com.mobileappdev.teamone.meetup.EventModels;

public class EventListItem {
    private Integer eventId;
    private String eventName;

    public EventListItem(Integer id, String name) {
        this.eventId = id;
        this.eventName = name;
    }

    public Integer getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }
}
