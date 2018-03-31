package com.mobileappdev.teamone.meetup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ChatListEventItem implements ChatListItem {
    private Integer id, attendees;
    private Double distance;
    private String eventName;
    private Date eventTime;

    public ChatListEventItem(Integer id, String eventName, Integer attendees, Double distance, Date eventTime) {
        this.id = id;
        this.attendees = attendees;
        this.distance = distance;
        this.eventName = eventName;
        this.eventTime = eventTime;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return eventName;
    }

    @Override
    public String getSubtitle() {
        return attendees + "Attendees";
    }

    @Override
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

    @Override
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
