package com.mobileappdev.teamone.meetup.EventModels;

import java.util.ArrayList;
import java.util.List;

public class EventListContent {

    public static List<EventListItem> getList() {
        ArrayList<EventListItem> _list = new ArrayList<>();

        for(int i = 0; i < 15; i++) {
            EventListItem eventListItem = new EventListItem(
                    i,
                    "Event " + i + " Name"
            );


            _list.add(eventListItem);
        }

        return _list;
    }
}
