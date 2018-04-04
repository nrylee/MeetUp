package com.mobileappdev.teamone.meetup;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateEventFragment.OnEventCreatedFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText createEventLocation;
    private EditText createEventStartTime;
    private EditText createEventEndTime;
    private EditText createEventName;

    private Spinner mySpinner;

    private OnEventCreatedFragmentInteractionListener mListener;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_event, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEventCreatedFragmentInteractionListener) {
            mListener = (OnEventCreatedFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences userdata = getContext().getSharedPreferences("userdata", Context.MODE_PRIVATE);
        float user_lat = userdata.getFloat("user_lat", 0);
        float user_lng = userdata.getFloat("user_lng", 0);

        mySpinner = view.findViewById(R.id.create_event_attendees_spinner);
        populateSpinner();

        createEventLocation = view.findViewById(R.id.create_event_location);
        createEventStartTime = view.findViewById(R.id.create_event_start_time);
        createEventEndTime = view.findViewById(R.id.create_event_end_time);
        createEventName = view.findViewById(R.id.create_event_name);

        createEventLocation.setText(user_lat + "," + user_lng);
        ((Button)view.findViewById(R.id.create_event_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dtf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                if (mListener != null) {
                    try {

                        List<Integer> attendees_id_list = new ArrayList<>();
                        for (AttendeeListSimpleSearch search : ((AdapterAttendeeSpinnerItem)mySpinner.getAdapter()).getListState())
                            if(search.isSelected() && search.getTagId() > -1)
                                attendees_id_list.add(search.getTagId());


                        mListener.onEventCreatedFragmentInteraction(
                                String.valueOf(createEventName.getText()),
                                dtf.parse(String.valueOf(createEventStartTime.getText())),
                                dtf.parse(String.valueOf(createEventEndTime.getText())),
                                String.valueOf(createEventLocation.getText()),
                                attendees_id_list
                        );

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void populateSpinner() {
        List<AttendeeListSimpleSearch> tagsNames = new ArrayList<>();



        AttendeeListSimpleSearch tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(-3);
        tagSpecific.setTagText(AdapterAttendeeSpinnerItem.oneSpace);
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(-2);
        tagSpecific.setTagText("select All Items");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(-1);
        tagSpecific.setTagText("remove All Items");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(0);
        tagSpecific.setTagText("Item 0");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(1);
        tagSpecific.setTagText("Item 1");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(2);
        tagSpecific.setTagText("Item 2");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(3);
        tagSpecific.setTagText("Item 3");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(4);
        tagSpecific.setTagText("Item 4");
        tagsNames.add(tagSpecific);

        tagSpecific=new AttendeeListSimpleSearch();
        tagSpecific.setTagId(5);
        tagSpecific.setTagText("Item 5");
        tagsNames.add(tagSpecific);

        final AdapterAttendeeSpinnerItem adapterTagSpinnerItem = new AdapterAttendeeSpinnerItem(getContext(), 0, tagsNames,mySpinner);
        mySpinner.setAdapter(adapterTagSpinnerItem);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEventCreatedFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEventCreatedFragmentInteraction(String name, Date startTime, Date endTime, String locationString, List<Integer> attendeeIds);
    }
}
