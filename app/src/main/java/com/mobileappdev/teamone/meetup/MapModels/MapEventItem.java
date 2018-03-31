package com.mobileappdev.teamone.meetup.MapModels;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapEventItem {
    private Integer eventId;
    private LatLng center;
    private Integer radius;
    private String eventName;
    private List<MapEventAttendee> eventAttendeeList;

    public MapEventItem(Integer id, String name, Double lat, Double lng, Integer radius) {
        this.eventId = id;
        this.eventName = name;
        this.center = new LatLng(lat, lng);
        this.radius = radius;
        this.eventAttendeeList = new ArrayList<>();
    }

    public boolean addAttendee(MapEventAttendee attendee) {
        if (!eventAttendeeList.contains(attendee))
            return eventAttendeeList.add(attendee);
        return false;
    }

    public LatLng getCenter() {
        return center;
    }

    public Integer getEventId() {
        return eventId;
    }

    public Integer getRadius() {
        return radius;
    }

    public List<MapEventAttendee> getEventAttendeeList() {
        return eventAttendeeList;
    }
    
    public int getColor() {
        Random r = new Random();
        int i = 0x55000000;
        return i + r.nextInt(0x0F000000);
    }
}
