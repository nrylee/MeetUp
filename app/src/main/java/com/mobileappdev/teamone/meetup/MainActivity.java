package com.mobileappdev.teamone.meetup;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.mobileappdev.teamone.meetup.DbRepository.Repository;
import com.mobileappdev.teamone.meetup.FragmentListeners.OnViewChatListener;
import com.mobileappdev.teamone.meetup.FragmentListeners.OnViewEventDetailListener;
import com.mobileappdev.teamone.meetup.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        OnViewEventDetailListener,
        OnViewChatListener,
        MapFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        EventsListFragment.OnCreateEventFragmentInteractionListener,
        EventDetailFragment.OnEditEventInteractionListener,
        EditEventFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener,
        NotificationFragment.OnListFragmentInteractionListener,
        CreateEventFragment.OnEventCreatedFragmentInteractionListener
{

    private TextView mTextMessage;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private EventsListFragment eventsListFragment;
    private ChatListFragment chatListFragment;
    private NotificationFragment notificationFragment;
    private MapFragment mapFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_events:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentWindow, eventsListFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_map:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentWindow, mapFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_chats:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentWindow, chatListFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentWindow, notificationFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationSync.startActionSyncLocation(this, null, null);

        eventsListFragment = new EventsListFragment();
        mapFragment = new MapFragment();
        chatListFragment = new ChatListFragment();
        notificationFragment = new NotificationFragment();

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentWindow, eventsListFragment);
        fragmentTransaction.commit();



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences defaultSharedPreferences = getSharedPreferences("userdata", MODE_PRIVATE);
        int user_id = defaultSharedPreferences.getInt("user_id", -1);
        if (user_id == -1) {
            //TODO: Implement Login Activity
            user_id = 1;
            defaultSharedPreferences.edit().putInt("user_id", user_id).apply();

            int user_id1 = defaultSharedPreferences.getInt("user_id", -2);
            if (user_id1 == -2) {
                int length = "".length();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onViewEventDetailInteraction(Integer eventId) {
        EventDetailFragment eventDetailFragment = EventDetailFragment.newInstance(eventId, null);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentWindow, eventDetailFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(MessageItem item) {

    }

    @Override
    public void onCreateEventFragmentInteraction() {
        CreateEventFragment createEventFragment = CreateEventFragment.newInstance(null, null);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentWindow, createEventFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onEditEventInteractionListener(Integer id) {
        EditEventFragment editEventFragment = EditEventFragment.newInstance(null, null);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentWindow, editEventFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onViewChatInteraction(Integer chatId) {
        MessageFragment messageFragment = MessageFragment.newInstance(null, null);

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentWindow, messageFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onEventCreatedFragmentInteraction(String name, Date startTime, Date endTime, String locationString, List<Integer> attendeeIds) {
        String[] split = locationString.split(",");
        Double lat = Double.valueOf(split[0]);
        Double lng = Double.valueOf(split[1]);

        List<Integer> event_ids = (new Repository()).InsertEvent(name, lat, lng, startTime, endTime, 150);
        if ( event_ids != null && event_ids.size() > 0 ) {
            List<Integer> user_ids = new ArrayList<Integer>(attendeeIds);

            SharedPreferences userdata = getSharedPreferences("userdata", MODE_PRIVATE);
            int userId = userdata.getInt("user_id", -1);
            if (userId != -1) {
                user_ids.add(userId);
                List<Integer> attendeeReferenceIds = (new Repository()).InsertUserInEvent(event_ids.get(0), user_ids);

                List<Integer> chat_ids = (new Repository()).InsertEventChat(event_ids.get(0));
                if(chat_ids.size()==1) {
                    List<Integer> chatUsersReferenceIds = (new Repository()).InsertUsersIntoChat(user_ids, chat_ids.get(0));
                }
                onViewEventDetailInteraction(event_ids.get(0));
            }
        }
    }
}
