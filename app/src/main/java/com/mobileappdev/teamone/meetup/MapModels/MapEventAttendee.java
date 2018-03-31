package com.mobileappdev.teamone.meetup.MapModels;

import com.google.android.gms.maps.model.LatLng;

public class MapEventAttendee {
    private Integer attendeeId;
    private String attendeeName;
    private LatLng center;

    public  MapEventAttendee(Integer id, String name, Double lat, Double lng) {
        this.attendeeId = id;
        this.attendeeName = name;
        this.center = new LatLng(lat, lng);
    }

    public LatLng getCenter() {
        return center;
    }
}
