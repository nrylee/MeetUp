package com.mobileappdev.teamone.meetup.EventModels;

import com.mobileappdev.teamone.meetup.DbRepository.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventListContent {

    public static List<EventListItem> getList() {
        List<EventListItem> _list = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            EventListItem eventListItem = new EventListItem(
                    i,
                    "Event " + i + " Name",
                    5,
                    Math.random() * 10000,
                    new Date()
            );


            _list.add(eventListItem);
        }

        return _list;
    }

    public static List<EventListItem> getListForUser(Integer user_id) {
        List<EventListItem> _list = new ArrayList<>();

        List<Repository.Event> eventList = (new Repository()).GetEventsForUser(user_id);

        for (Repository.Event event:eventList) {
            EventListItem eventListItem = new EventListItem(
                    event.event_id,
                    event.event_name,
                    event.AttendeesCount,
                    event.event_longitude,
                    event.event_start
            );
            _list.add(eventListItem);
        }

        return _list;
    }
}
