package com.mobileappdev.teamone.meetup.EventModels;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class EventListItem {
    private Integer eventId;
    private String eventName;
    private Integer attendees;
    private Double distance;
    private Date eventTime;

    public EventListItem(Integer id, String name, Integer attendees, Double distance, Date eventTime) {
        this.eventId = id;
        this.eventName = name;
        this.attendees = attendees;
        this.distance = distance;
        this.eventTime = eventTime;
    }

    public Integer getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }


    public String getAttendee() {
        return attendees + "Attendees";
    }


    public String getStatus() {
        long difference = getDateDiff(eventTime, new Date(), TimeUnit.MINUTES);
        String pretext = "Starts in ";
        if (difference<0) {
            pretext = "Ends in ";
            difference = difference * -1;
        }
        if (difference > 60) {
            return pretext + TimeUnit.HOURS.convert(difference, TimeUnit.MINUTES) + "hrs";
        }
        else if(difference<1) {
            return pretext + "< 1min";
        }
        else {
            return pretext + difference + " mins";
        }
    }


    public String getSubStatus() {
        if (distance<10) {
            return "Attending";
        }
        else if(distance > 400) {
            Double tempDistance = distance/5280;
            return tempDistance.toString().substring(0,3) + " mi";
        }
        else {
            return distance.intValue() + " ft";
        }
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
