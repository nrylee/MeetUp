package com.mobileappdev.teamone.meetup;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.mobileappdev.teamone.meetup.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements
        EventsListFragment.OnListFragmentInteractionListener,
        ChatListFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener
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



        /*mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Button sendButton =
                (Button)findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {//used to add text to the list (or "send" it) when the send button is clicked
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                //make "sent" message object type ChatMessage
                ChatMessage message= new ChatMessage(input.getText().toString(), "John");
                ListView messageList=(ListView)findViewById(R.id.list_of_messages); //the list that is on the screen that is meant to show sent texts (from fragment_chatlist.xml)
               // messageList.add(message); // this is where we would need the adapter?
                // Clear the input
                input.setText("");
            }
        }); */
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(ChatListItem item) {
        ChatFragment chatFragment = ChatFragment.newInstance(item.getId());

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentWindow, chatFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(MessageItem item) {

    }
}
