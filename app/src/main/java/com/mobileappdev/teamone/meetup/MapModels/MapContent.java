package com.mobileappdev.teamone.meetup.MapModels;

import com.mobileappdev.teamone.meetup.DbRepository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapContent {

    public final static double LAT_START = 33.755420;
    public final static double LNG_START = -84.387090;

    public static List<MapEventItem> getMapEvents(Integer user_id) {
        Repository repository = new Repository();
        List<MapEventItem> _list = new ArrayList<>();

        List<Repository.Event> eventsForUser = repository.GetEventsForUser(user_id);
        for(Repository.Event userEvent:eventsForUser) {
            MapEventItem mapEventItem = new MapEventItem(
                    userEvent.event_id,
                    userEvent.event_name,
                    userEvent.event_latitude,
                    userEvent.event_longitude,
                    userEvent.event_radius
            );
            for(MapEventAttendee attendee:repository.GetEventAttendees(userEvent.event_id)) {
                mapEventItem.addAttendee(attendee);
            }

            _list.add(mapEventItem);
        }

        return _list;
    }

    public static List<MapEventItem> getMapEvents() {
        ArrayList<MapEventItem> _list = new ArrayList<>();

        Random r = new Random();

        Integer totalEvents = r.nextInt(10) + 5;
        for (int i = 0; i < totalEvents; i++) {
            double eventCenterLat = LAT_START + ((r.nextDouble() * 0.5) - 0.25);
            double eventCenterLng = LNG_START + ((r.nextDouble() * 0.5) - 0.25);

            MapEventItem mapEventItem = new MapEventItem(
                    i,
                    "Event ID " + i,
                    eventCenterLat,
                    eventCenterLng,
                    r.nextInt(100) + 100
            );
            _list.add(mapEventItem);


            Integer totalAttendees = r.nextInt(60) + 2;
            for (int j = 0; j < totalAttendees; j++) {
                double attendeeCenterLat = eventCenterLat + ((r.nextDouble() * 0.001) - 0.0005);
                double attendeeCenterLng = eventCenterLng + ((r.nextDouble() * 0.001) - 0.0005);

                MapEventAttendee eventAttendee = new MapEventAttendee(
                        i + j,
                        "Attendee " + (i+j),
                        attendeeCenterLat,
                        attendeeCenterLng
                );
                mapEventItem.addAttendee(eventAttendee);
            }
        }

        /*Repository repo = new Repository();
        for (MapEventAttendee a:repo.GetEventAttendees()) {
            _list.get(0).addAttendee(a);
        }*/
        return _list;
    }
}
