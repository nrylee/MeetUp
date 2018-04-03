package com.mobileappdev.teamone.meetup;

import android.net.Uri;
import android.os.Message;
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

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mobileappdev.teamone.meetup.FragmentListeners.OnViewChatListener;
import com.mobileappdev.teamone.meetup.FragmentListeners.OnViewEventDetailListener;
import com.mobileappdev.teamone.meetup.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements
        OnViewEventDetailListener,
        OnViewChatListener,
        MapFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,
        EventsListFragment.OnCreateEventFragmentInteractionListener,
        EventDetailFragment.OnEditEventInteractionListener,
        EditEventFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener
{

    private TextView mTextMessage;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private EventsListFragment eventsListFragment;
    private ChatListFragment chatListFragment;
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
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventsListFragment = new EventsListFragment();
        mapFragment = new MapFragment();
        chatListFragment = new ChatListFragment();

        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentWindow, eventsListFragment);
        fragmentTransaction.commit();



        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onViewEventDetailInteraction(Integer eventId) {
        EventDetailFragment eventDetailFragment = EventDetailFragment.newInstance(null, null);

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
}
